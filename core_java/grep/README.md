# Introduction
The Java Grep Application is an application that outputs mathematically formatted lines in a text file after searching a file for a particular pattern. The application filters recursively over the subfolders using the root directory as input. To read and write into files, the BufferReader, FileReader, BufferWriter, and FileWriter are used. Maven is used to creating the application and manage dependencies. Docker is used to package the program, which is then submitted to Docker Hub.

# Quick Start
There are two ways to use the app, firstly 3 inputs  are required;

- ```` regex ```` : The pattern to be searched for in the file.
- ```` rootpath ```` : The path directory
- ```` outfile ```` : File that contains the matched lines.

Run using jar file:
````
# build package with maven
 mvn clean compile package
 java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp ${regex} ${rootpath} ${outfile}
````

Run using Docker Image:
````
# pull docker image
docker pull fatemehkhaksar/grep
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log fatemehkhaksar/grep ${regex} ${rootpath} ${outfile}
````

# Implemenation
## Pseudocode
````
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
        
writeToFile(matchedLines)
````

## Performance Issue
If Java is processing a huge file, an OutOfMemoryException will be thrown due to Java's constrained heap memory space.
This occurs as a result of the memory space-consuming storage of List instances that are returned.
By performing intermediate operations up until the terminal operation and not storing them in the heap memory, the Stream API implementation will fix this problem.

# Test
Manual testing was done on the program. The software was ran with several test cases by implementing various patterns and directories, and errors were recorded using Log4j.
To determine whether the matched lines were properly documented, the output file was examined.

# Deployment
To make the grep app more accessible, it was dockerized. Log in to Docker first, then create the Docker image.
````
# package with maven
mvn clean package
# build the docker image locally
docker build -t ${docker_user}/grep .
````
Then push the image to docker hub
````
docker push ${docker_user}/grep
````

# Improvement
- Add the ability to skip some files if the user doesn't want all the files in the directory to be searched. 
- Provide more information about matched lines in the files.
- Use the Stream API to improve memory efficiency.