#!/bin/sh
BEANSTALK_ENV_NAME="$(/opt/elasticbeanstalk/bin/get-config container -k environment_name)"

cat > /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.d/webapp.json <<EOF
{
  "logs": {
    "logs_collected": {
       "files": {
         "collect_list": [
           {
              "file_path": "/home/webapp/logs/webapp.log",
              "log_group_name": "/aws/elasticbeanstalk/${BEANSTALK_ENV_NAME}/home/webapp/logs/webapp.log",
              "log_stream_name": "{instance_id}",
              "timestamp_format": "%Y-%m-%d %H:%M:%S.%f",
              "multi_line_start_pattern": "{timestamp_format}"
           }
         ]
       }
    }
  }
}
EOF

systemctl restart amazon-cloudwatch-agent.service
