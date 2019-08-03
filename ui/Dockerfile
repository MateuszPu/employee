FROM node:10

ADD src webapp/src
ADD angular.json webapp/angular.json
ADD package.json webapp/package.json
ADD proxy.conf.json webapp/proxy.conf.json
ADD tsconfig.json webapp/tsconfig.json
ADD tslint.json webapp/tslint.json 

WORKDIR webapp

RUN apt-get update && \
    npm install && \
    npm install -g @angular/cli

CMD ["ng", "serve", "--host", "0.0.0.0", "--proxy-config", "proxy.conf.json"]
