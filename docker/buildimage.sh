docker stop nervecenter
docker rm nervecenter
docker rmi -f nervecenter:latest
docker build ./.. --file=dockerfile --tag=nervecenter