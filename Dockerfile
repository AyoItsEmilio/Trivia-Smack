FROM ubuntu:14.04.1

RUN apt-get update -y
RUN apt-get install -y python-pip python-dev build-essential
COPY . .
RUN pip install -r requirements.txt

EXPOSE 5000

CMD ["python", "application.py"]
