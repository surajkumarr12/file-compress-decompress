# file-compress-decompress

> This program compresses files and folders into a set of compressed
> files such that of each compressed file doesn’t exceed a maximum size
> provided in the input. The same program can be used for decompressing
> the files that it has generated earlier. The output of the
> decompression should be identical to the original input of the
> compression process.
> 
> This program doesn't use any third party libraries for compression or
> decompression. Inbuilt ZIP algorithm in java is used.  File size can
> be greater than JVM memory. This generates the absolute minimum number
> of compressed files. 
> Unit and Integration tests are added with code coverage of 80% and Jacoco report is generated 


# How to use
	Main method accepts following commands.
	
## To Compress
	## compress inputdirPath outputdirPath maxCompressFileSizeInMB(Integer)
	

## To Decompress
	## decompress inputdirPath outputdirPath
	
# How to build and test project

 - Clone the repository
 - **Build** : ./gradlew build (this will build the project and run unit and integration tests with Jacoco reports) 
	 testFiles in test/resources contains test files
	 zip  in test/resources contains zipped files
	 unzip  in test/resources contains unzipped files
    View test results :  After build jacoco report will be available in /build/reports/
	 
 - **Run** :  
 **-To compress** ./gradlew run --args='compress inputdirPath outputdirPath maxCompressFileSizeInMB(Integer) '
  *Example* : ./gradlew run --args='compress src/test/resources/testFiles/ src/test/resources/zip/ 2'
  **-To decompress** ./gradlew run --args='decompress inputdirPath outputdirPath'
  *Example* : ./gradlew run --args='compress src/test/resources/zip/ src/test/resources/unzip/'
