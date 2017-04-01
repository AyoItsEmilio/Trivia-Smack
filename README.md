# Trivia Smack
A fast-paced 2 player trivia game.

As of right now:
A Flask-MongoDB stack running on a Docker EB platform. A single page application made with Knockout.
Click start to have 3 random questions thrown at you. Answer fast to get the highest score! Your score is based on 
how fast you answer the question.
Play two player mode on different computers or on different tabs locally. A player on the Android App can play someone on the web app.
There's also an admin page to manage questions. It's up to you to figure out the login credentials.

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
- Unit tests: `python -m web_app.run_unit_tests`
- Integration tests: `python -m web_app.run_integration_tests`

You can find the Android tests in the usual Android test folder.

Take a look at the wiki to see where to find what.
