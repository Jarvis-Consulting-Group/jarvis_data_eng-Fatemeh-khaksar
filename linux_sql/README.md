
# Introduction
Linux cluster is a group of Linux computers or nodes, storage devices that work together and are managed by the Linux Cluster Administration (CLA) .
The nodes are connected to each other through a switch.The CLA needs to have each servers information(hardware and cpu usage).The Agent consists of host_info.sh and host_usage.sh which collects hardware specifications and resource usage ,respectively. The collected data is stored in database every minute by cron job.
# Quick Start

- Start a psql instance using psql_docker.sh
```
./scripts/psql_docker.sh start
```
- Create tables using ddl.sql
```
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```
- Insert hardware specs data into the DB using host_info.sh
```
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
- Insert hardware usage data into the DB using host_usage.sh
```
./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
- Crontab setup
```
crontab -e

#add this line to crontab
* * * * * bash /path/to/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
```

# Implemenation
- Setup Github Repository
- Create develop and release branches
- Setup psql instance using docker
- Collect information about host hardware and host cpu usage
- Create host_info and host_usage tables by SQL DDL Commands in host_agent sql
- insert the collected data into the created tables in database by host_info.sh and host_usage.sh bash scripts
- finally, the data is stored every minute by using crontab
## Architecture
. Image must be saved to the `assets` directory.

## Scripts

- psql_docker.sh
The script is used to create ,start and stop the psql instance on docker.
```
./scripts/psql_docker.sh start|stop|create [db_username][db_password]

# create an instance
./scripts/psql_docker.sh create db_username db_password

#start the instance
./scripts/psql_docker.sh start

#stop the instance
./scripts/psql_docker.sh stop
```
- host_info.sh
this script gathers the host hardware data and then the data is inserted into the psql database. This script is executed once.

```
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
- host_usage.sh
This script gathers the host usage data and then the data is inserted into the psql database every minute by cron job.
```
./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
- crontab
Is used to execute the host_usage script every minute.
```
# edit crontab job
crontab -e

# add this line to crontab, create a crontab job
* * * * * bash /path/to/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log

#list crontab jobs
crontab -l
```

- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling

- `host_info`

| Field Name    | Data Type | Constraints |
|---------------|-------|------|
| id	           | serial| PK NOT NULL |
| hostname	     | varchar | UNIQUE NOT NULL |
| cpu_number    | 	int2 | 	NOT NULL |
| cpu_architecture | 	varchar| 	NOT NULL|
| cpu_model	    | varchar	 | NOT NULL |
| cpu_mhz	      | float8	 | NOT NULL |
| l2_cache	     | int4	 | NOT NULL |
| "timestamp"	  |timestamp	| NULL |
| total_mem	    |int4	| NULL |

- `host_usage`

|Field Name	| Data Type	 |Constraints|
|-----------|------------|-----------|
|"timestamp"| 	timestamp |	NOT NULL|
|host_id	| serial	FK  |NOT NULL|
|memory_free| 	int4	     |NOT NULL|
|cpu_idel	| int2	      |NOT NULL|
|cpu_kernel	| int2	      |NOT NULL|
|disk_io	|int4	|NOT NULL|
|disk_available	|int4	|NOT NULL|

# Test
How did you test your bash scripts DDL? What was the result?

# Deployment
How did you deploy your app? (e.g. Github, crontab, docker)

# Improvements
Write at least three things you want to improve
e.g.
- handle hardware updates
- blah
- blah