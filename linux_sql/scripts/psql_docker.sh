nd line arguments
cmd=$1
db_username=$2
db_password=$3

#check docker status, start if not started
sudo systemctl status docker || systemctl start docker

#check if jrvs-psql exists
docker container inspect jrvs-psql
container_status=$?

case $cmd in
create)
  #if container exists then exit
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1
  fi

  #if arguments is not equals to 3 then exist with a message
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  #if container doesn't exist and arguments are 3, then create volume and container
  docker volume create pgdata

  docker run --name jrvs-psql -e POSTGRES_USER="$db_username" -e POSTGRES_PASSWORD="$db_password" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine

  exit $?
  ;;

  #if argument is start/stop
start | stop)
  #check instance status; exit 1 if container has not been created
  if [ $container_status -eq 0 ]; then
    #Start or stop the container
    docker container "$cmd" jrvs-psql
    echo "Container $cmd"
    exit $?
  else
    echo "container has not been created"
    exit 1
  fi
  ;;

  #if arguments are not start/stop/create then exit with message
*)
  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac
