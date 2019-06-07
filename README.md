iSPD - iconic Simulator of Parallel and Distributed Systems
===

iSPD is a simulator with a friendly user interface for modelling computing grid and computing clouds, with their workloads, virtual machines and schedulers, besides the hardware structure involved, allowing the analisys of processing, money costs, scheduler eficiency and much more.

Release Notes
===

1. Running on Java 12
2. Using Gradle v5.4 to build the project
3. Idioma (language pack) has been moved from 'gspd.ispd.idioma' to 'src/main/resources/idioma'
4. Images has been moved from 'gspd.ispd.gui.imagens' to 'src/main/resources/images'
5. DTD (Data Type Description) package has been moved from 'gspd.ispd.arquivo.xml.dtd' to 'src/main/resources/dtd'

Problems
===

Please, report problems in issues
# 1. Currently we are fighting trouble to open saved files (.imsx) 😢 but as soon as possible it will be solved
# 2. Trace files (.wmsx) are also not being correctly recovered for simulation

Development
===

This project uses Gradle, if you are going to develop it in a common code editor, please check Gradle manual at (https://docs.gradle.org/current/userguide/userguide.html). If you are going to develop it on a IDE like NetBeans, IntelliJ IDEA, Eclipse, ... then open the project as a "Gradle Project".

Anyway, in order to build this project, make sure you have Java and Gradle correctly intalled on your system, go to project root directory and execute `./gradlew build`.

To run the compiled program, execute `./gradlew run`, or you can extract the archive from 'build/distribution' and execute 'bin/ispd'.

TODO
===

Here is the things that was yet to be done in iSPD:

1. Solve such problems noted in Problems section
2. Solve the Copy/Paste function that currently is not working always (or does not copy everything)
3. Add to functions used in 'iSPD.jar' those ones supported in 'org.apache.commons.math', those are more optimized and more dependency-friendly
4. Implement a way to modelling task dependency in cloud. It envolves (so far):
    1. Change 'iSPDcargas.dtd' structure in order to aquire the dependencies. Therefore, change the trace file format
    2. Change the simulation motor to accept this new way of modelling
5. Create a class User to treat more information about it, whether than simply using String's to represent the users

Good cloud!