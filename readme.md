<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a>
    <img src="src/resources/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">GeCiFa : Gestión Clientes y Facturas </h3>

  <p align="center">
    Freelance project
    <br />
  </p>
</p>

<!-- ABOUT THE PROJECT -->
## About The Project

<p align="center">
<img src="https://github.com/Atlantis2723/gecifa/blob/master/src/resources/demoFiles/inicio.png?raw=true" width="800">
</p>
Desktop app for a small financial advisory, which consists of a desktop app for managing clients recurring invoices and for automating repetitive tasks.

For more context on the development of the app read my Linkedin [post](https://www.linkedin.com/in/oscar-izquierdo-valentin/).

The project is divided in the following sections:
* [Clients](https://github.com/Atlantis2723/gecifa/blob/master/src/resources/demoFiles/inicio.png?raw=true)
* [Concepts](https://github.com/Atlantis2723/gecifa/blob/master/src/resources/demoFiles/conceptos.png?raw=true)
* [Invoices](https://github.com/Atlantis2723/gecifa/blob/master/src/resources/demoFiles/facturas.png?raw=true)
* [Configuration](https://github.com/Atlantis2723/gecifa/blob/master/src/resources/demoFiles/cfg.png?raw=true)

Each of these sections holds the information relevant to the topic,each with its own options on a top bar.

### Built With

Essentially the app uses a json file system (I did not know how to use databases back then) and all the logic is implemented using plain java with the exception of the json parsing libraries. 
The UX is built using **Java Swing**.

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* Java 1.7 or above


### Set up

0. You may need to put [this dll](https://github.com/Atlantis2723/gecifa/blob/master/src/resources/demoFiles/facturas.png?raw=true) in your `java/bin` folder

2. Download and click on the executable. Local directory `GECIFA` should be created on `AppData/Local`

3. Enter the `MODELOS` section, which will open a file explorer instance for you to place the .xlsx files there (see Roadmap)   

4. Introduce Clients and Concepts Data

5. Go to [`CONFIGURACIÓN`](https://github.com/Atlantis2723/gecifa/blob/master/src/resources/demoFiles/jacob-1.15-M3-x64.dll) and set your payment options as well as invoice numbers.

6. Go to `MODELOS` and paste the downloaded [excel blueprints](https://github.com/Atlantis2723/gecifa/tree/master/modelos)

7. Modify these models with your data and signature

8. You are all set!



<!-- USAGE EXAMPLES -->
## Usage

The interface is very intuitive so you should not have much trouble using it. The only thing you should keep an eye on are the `red warnings` on some menus which explain how are the values saved so you don't lose any data.

<!-- ROADMAP -->
## Roadmap

Even though the app is already finished, lots of ideas came up during development:

* Email integration for automated invoice mailing
* Various invoice models support
* Database transition
* Performance improvements on file creation
* Make model installation automatic so that the user doesn't have to do it manually

## Contact

Óscar Izquierdo Valetín - [Linkedin](https://www.linkedin.com/in/oscar-izquierdo-valentin/)