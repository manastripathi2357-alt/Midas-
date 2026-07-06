Completed the JP Morgan Chase Job Simulation of Software Engineering 

**Asynchronous Message Ingestion**: Configured an Apache Kafka consumer pipeline (midas-group) with explicit JsonDeserializer mappings to reliably ingest transaction streams.


**Atomic Business Validation:** Validates transactions entirely in-memory before database interaction, ensuring structural integrity and sufficient sender balances prior to executing transfers.

**
Relational Data Persistence**: Persists validated records to an H2 database utilizing Spring Data JPA and strict @ManyToOne foreign key normalization to prevent data duplication.


**External API Integration**: Executes synchronous HTTP POST requests via RestTemplate to calculate and allocate bonus rewards dynamically without deducting from the sender.


**RESTful API**: Built a scalable GET /balance endpoint exposed on port 33400 to serve real-time JSON account profiles based on a user ID parameter.


**Containerized Testing:** Orchestrated isolated integration environments using Docker and Testcontainers, resolving runtime crashes by pinning stable Zookeeper image versions to achieve a 100% pass rate.

