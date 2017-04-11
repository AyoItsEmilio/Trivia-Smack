# Trivia Smack
A fast-paced trivia game.

A flask-MongoDB stack running on a Docker EB platform; single page application made with Knockout

This [wiki page](https://github.com/samuel-peers/Trivia-Smack/wiki/Application-Functionality) has details on the application's features.

Go here http://triviadockerenvgood.irs2mc4qmz.us-west-2.elasticbeanstalk.com/index.html to play.

Go here http://triviadockerenvgood.irs2mc4qmz.us-west-2.elasticbeanstalk.com/admin.html to access the admin page.

Uses AWS Codepipeline for Continuous Integration. Code changes trigger deployment to our production server in AWS (http://triviadockerenvgood.irs2mc4qmz.us-west-2.elasticbeanstalk.com. It's purely RESTful so don't expect to see anything when you go to the url).

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
- Open Android Studio and open the project `android_app`

Note the Android app sends requests to our production server mentioned above. Change the base url in `android_app > ... > business > ServerAccessObject.java` to point the app to your server, if you want.

To run server tests:
- Unit tests: `python -m web_app.tests.run_unit_tests`
- Integration tests: `python -m web_app.tests.run_integration_tests`

You can find the Android tests in the usual Android test folder.

Take a look at the wiki to see where to find what.
