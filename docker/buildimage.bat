docker stop sailor
docker rm sailor
docker rmi -f sailor:latest
docker build ./.. --file=dockerfile --tag=sailor