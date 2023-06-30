

# Introduction
### purpose behind this project
This project's main goal is to investigate and assess Apache Hive's capabilities inside the Hadoop environment as a tool for tackling and resolving challenging business issues. 
We seek to effectively handle massive amounts of data and extract valuable information by utilising the power of Hadoop clusters and distributed technology.

Learnings and Assessments
We have studied and assessed a number of core Hadoop components during the course of this project, including MapReduce, HDFS, and YARN. Using Dataproc, we set up a Hadoop cluster on the Google Cloud Platform (GCP), which allowed us to manage and extend our resources as necessary.

To tackle particular business issues, we used Apache Hive and the Zeppelin Notebook, practicing various HiveQL queries and evaluating the effectiveness of various approaches. Some of the methods we employed are as follows:

- searching with and without cache to compare the performance
- Optimizing data retrieval by comparing partitioned vs. non-partitioned queries
- Using CSV SerDe to fix parsing errors will enhance data management.
- Utilizing columnar file formats to improve data access and storage
# Hadoop Cluster

 ![my image](./assets/hadoop.png)

Big Data Tools
- Hadoop (MapReduce,YARN,HDFS)
- Hive
- Zeppelin
- Spark
Hardware Specifications
- Our Hadoop cluster comprised one master node and two worker nodes, each equipped with 2 CPUs, 16 GB of memory, and 100 GB of primary storage. In addition, we installed the Zeppelin notebook to manage HiveQL queries, enabling us to interact with our data in an organized and efficient manner.

# Hive Project
Optimizing  Hive queries
- Parsing problems can be fixed by using OpenCSVSerde: OpenCSVSerde is an Apache Hive-specific serializer-deserializer (SerDe) that aids in processing CSV data. We were able to tackle parsing problems caused by embedded delimiters, quotes, or escape characters while working with complicated CSV data using OpenCSVSerde.
- Partitioning (by year) can speed up query execution: In our project, we divided the data into year-based divisions, which made it possible to run queries more quickly because only the relevant partitions were accessible, instead of scanning the full dataset.
- Increasing query speed using columnar file optimisation and Parquet: By storing our data in a columnar format like Parquet, we were able to achieve greater compression and faster query execution since fewer I/O operations were required to access only the necessary columns.
- Execution speeds of MapReduce and Spark were compared in order to better understand how well each programme performed in the context of our particular use case. The findings showed that Spark outperformed MapReduce in most situations, particularly for iterative and sophisticated queries.


 ![my image](./assets/HiveProject.png)
# Improvements
- Addressing interpreter issues: During the project, we encountered issues with the Markdown and Shell Script interpreters in Apache Zeppelin, specifically the "Interpreter process is not running" error. To resolve these issues, we can investigate the root causes, such as checking for configuration errors, ensuring the required dependencies are installed, and verifying that the interpreter settings are correctly configured.
- Bucketing and user-defined functions are included: We can also practice tasks that include bucketing and user-defined functions (UDFs) to increase our understanding of and experience with Apache Hive. While UDFs allow users to build custom functions to handle data, bucketing is a method used in Hive to increase query speed by organizing data based on a particular column.
- Different SerDes and binary storage formats comparison: The goal of our present project was to use OpenCSVSerde to parse CSV data. Sequence Files and Avro data files are two more SerDes and binary storage formats that may provide various performance characteristics or functionality.
