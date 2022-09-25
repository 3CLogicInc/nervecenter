docker stop fusion
docker rm fusion
docker rmi -f fusion:latest
docker build ./.. --file=dockerfile --tag=fusion