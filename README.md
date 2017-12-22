# my-beanstalk
This is an implementation of beanstalk protocol. This is implemented for learning purpose. Following beanstalk commands are implemented:
* DELETE
* PUT
* QUIT
* RESERVE
* USE
* WATCH

## Building and Running the project
Following tools/packages are required to build and run the project.
* Java 7 or above
* Maven
* Git

Follow the steps given below to build and run the project.
### Git Clone
Clone the repository
```
git clone https://github.com/sathyatvrcbe/my-beanstalk.git
```
### Change Directory
Let's move to the project directory!
```
cd my-beanstalk/BeanstalkdClone
```
### Compile
Run this maven command in your console to compile and package the project with its dependencies
```
mvn clean dependency:copy-dependencies package -DskipTests
```
### Run
Start the JVM
```
java -cp target/*:target/dependency/* software.mybeans.App
```

## Performance
Some basic tests are conducted on the development environment.

* 10**6 jobs are inserted using a single client. my-beanstalk could insert 17,500 jobs per second whereas beanstalk could do the same at the rate of 25,000 inserts per second.
* 10**6 jobs are reserved and deleted using a single client. my-beanstalk could serve 8300 jobs per second whereas beanstalk could do the same at the rate of 12,300 per second.
