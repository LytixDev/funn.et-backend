<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->




<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Funn REST API</h3>

  <p align="center">
    API for the Funn project, a project for IDATT2105 at NTNU.
    <br />
    <a href="DocsLinks"><strong>Explore the docs »</strong></a>
  </p>
</div>


<!-- ABOUT THE PROJECT -->
## About The Project

This is a project task made for the course IDATT2105 at NTNU. This repository is for the API part of the task. The frontend for the project can be found [here](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-frontend/). 
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

To run this application you will need
- JDK 17
- Maven 
- Make
- Docker
- Docker-compose

### Installation and running


1. Clone the repo
   ```sh
   git clone git@gitlab.stud.idi.ntnu.no:ntcc/fullstack-backend.git
   ```
2. Change directory into the repo
   ```sh
   cd fullstack-backend
   ```
3. Add the environment variables to a .env file, see .env.example for an example
   ```sh
   cp .env.example .env
   ```
4. Start the database (note: docker must be running)
   ```sh
   make database
   ```
5. Start the application
   ```sh
   make
   ```

### Other commands
- `make database-down` - Deletes the database
- `make test`- Runs all tests
- `make install` - Installs the project
- `make compile` - Compiles the source code
- `make prettier` - Format source code


### Project structure

- [docker](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/docker) - Dockerfile
- [test](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/test/java/edu/ntnu/idatt2105/funn) - Unit and integration tests
- [resources](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/resources) - Spring boot config file and test data
- [controller](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/controller) - REST Controller classes.
- [dto](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/dto) - Data transfer objects
- [exceptions](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/exceptions) - Custom created exceptions
- [mapper](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/mapper) - Classes that map dto's to model classes
- [model](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/model) - Model classes. Also known as entities in the spring world.
- [repository](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/repository) - Repository interfaces that manages the model classes data
- [security](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/security) - JWT security implementation and configuration
- [service](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/tree/main/src/main/java/edu/ntnu/idatt2105/funn/service) - All services


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap
Some issues are marked with the `further-work` label. These are features we would have liked to include in the final project, but were not implemented due to time constraits. Some features may also require changes in the [frontend](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-frontend).

Some issues are marked with the `known-bug` label. These are bugs we have identified and are yet to fix.

See the [open issues](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend/-/issues) for a full list of proposed features (and known issues).


<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTRIBUTING -->
## Contributing
If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
2. Commit your Changes (`git commit -m 'Add some AmazingFeature'`). Remember to run `make prettier` to format the code.
3. Push to the Branch (`git push origin feature/AmazingFeature`)
4. Open a Pull Request


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Nicolai H. Brand - nicolahb@stud.ntnu.no
Callum Gran - callumg@stud.ntnu.no
Carl Gützkow - carljgu@stud.ntnu.no
Thomas H. Svendal - thosve@stud.ntnu.no

Project Link: [https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend](https://gitlab.stud.idi.ntnu.no/ntcc/fullstack-backend)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
