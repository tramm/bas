FROM nginx
MAINTAINER tramm

COPY nginx.conf /etc/nginx/nginx.conf
RUN mkdir -p /etc/nginx/sites-available
COPY proxy_params /etc/nginx/proxy_params
COPY bas.conf /etc/nginx/sites-available/bas.conf
COPY api.conf /etc/nginx/sites-available/api.conf