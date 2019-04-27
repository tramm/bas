FROM nginx
MAINTAINER tramm

COPY nginx.conf /etc/nginx/nginx.conf
RUN mkdir -p /etc/nginx/sites-available
COPY bas.conf /etc/nginx/sites-available/bas.conf
COPY api.conf /etc/nginx/sites-available/api.conf