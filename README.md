# Trivia Smack
A fast-paced trivia game.

A Flask-MongoDB stack running on a Docker EB platform; front end made with Knockout.

This [wiki page](https://github.com/samuel-peers/Trivia-Smack/wiki/Application-Functionality) has details on the application's features.

Uses AWS Codepipeline for Continuous Integration. Code changes trigger deployment to our production server in AWS.

Requirements: eb CLI (`pip install awsebcli`)

To run the server:
- `clone https://github.com/samuel-peers/Trivia-Smack.git`
- `cd Trivia-Smack`
- `eb init -p Docker1.11.2 trivia_app`
- `eb create trivia-env`
- Go to whatever your server's url is `/index.html`.

You MUST be in the US West (Oregon) region or Canada (Central) for the custom AMI to work.

To run the android app:
- `clone https://github.com/samuel-peers/Trivia-Smack.git` (if you haven't already)
- `cd Trivia-Smack`
- change `SERVER_URL` in application > Constants.java to your AWS server address
- Open Android Studio and open the project `android_app`

To run server tests:
- Unit tests: `python -m web_app.tests.run_unit_tests`
- Integration tests: `python -m web_app.tests.run_integration_tests`

You can find the Android unit, integration, and acceptance tests in the usual Android test folders.
