# Money Lion Test Task

## About

This project covers Scenario 1 and Scenario 2 plus RestfulBooker API test from the test tasks.
I did not do Scenario 3 due to time constrains.

The project is built on docker-selenium image ( https://github.com/revof11/docker-selenium ) plus my own modifications such as maven, nano, python3-pip, netstat, htop, etc.

I fetched maven projects ( https://github.com/revof11/docker-selenium ) for faster startup.

Disclaimer: I have tried to execute Scenarios as closer to definition in the task as possible.
Occasional issues may arise.

## Instructions

> Prerequisites: 
> Install Docker at https://docs.docker.com/v17.09/engine/installation/
> Install vncviewer from 

1. Get the Docker tarball from `docker.link` with `cat docker.link | wget -i`
2. Import tarball to Docker with `docker load < ~/chrome-node-original.tar`
3. Run container with 
`docker run -d -e SCREEN_WIDTH=1366 -e SCREEN_HEIGHT=768 -p 5900:5900 -p 4444:4444 -v /dev/shm:/dev/shm -v ~/dockershare:/home/seluser/code chrome-node-original`
4. Open shell in container (assuming this only one container running) with 
`ID=$(docker ps | awk '{printf $1}' | cut -c 10-);docker exec -it $ID /bin/bash;echo $ID;`
5. Change folder to home with `cd ~/code/moneylion-example`
6. Launch the build of this project using `mvn -U clean test`
7. Find the screenshots from each test run in `~/code/screenshots`
8. Find reports in `code/moneylion-example/target/surefire-reports` folder
9. Launch API test with `python3 /home/seluser/code/moneylion-example/apitest/unifiTestWrapper.py`
10. Find reports in `python3 /home/seluser/code/moneylion-example/apitest/reports/report` folder