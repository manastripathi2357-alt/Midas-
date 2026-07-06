package com.jpmc.midascore; // Make sure this matches your package

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.jpmc.midascore.foundation.Incentive;

@Component
public class TransactionListener {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {

        // 1. Fetch the Users
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // 2. Apply the 3 Rules
        if (sender != null && recipient != null && sender.getBalance() >= transaction.getAmount()) {

            // 1. Ask the API for the incentive
            RestTemplate restTemplate = new RestTemplate();
            Incentive incentiveObj = restTemplate.postForObject("http://localhost:8080/incentive", transaction, Incentive.class);

            // Safety check just in case the API fails
            float incentiveAmount = (incentiveObj != null) ? incentiveObj.getAmount() : 0.0f;

            // 2. Adjust the balances (Sender loses transaction amount, Recipient gains transaction + incentive)
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

            // 3. Save the updated users
            userRepository.save(sender);
            userRepository.save(recipient);

            // 4. Save the full transaction record
            TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount);
            transactionRecordRepository.save(record);
        }
        // If the rules fail, we do nothing and the transaction is discarded.
    }
}