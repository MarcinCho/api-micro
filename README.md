
# Api-micro

Is a project that aims to create a couple of microservices with client app.
The goal of project is to explore microservice architecture as well as to get to know popular frameworks like Spring Boot and Quarkus, without any guidence.

### Api-pull

A quarkus microservice utilizing Github API to perform CRUD operations on users and repos.

The main objective of this project was to:
- Utilize Quarkus framework 
- Implement Java's virtual threads
- play with testing frameworks
- utilize Jakarta's http client
- exercise building microservices
## Tech Stack

**Client:** React, Redux, TailwindCSS

**Server:** Node, Express


## Run Locally

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run start
```


## Features

- Light/dark mode toggle
- Live previews
- Fullscreen mode
- Cross platform


## API Reference

#### Get all items

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.


## Usage/Examples

```javascript
import Component from 'my-project'

function App() {
  return <Component />
}
```


## Roadmap

- [ ] work on moduel naming.
- [ ] create docker compose file

### Api-pull (MS)

- [ ] add one to many relationship users -> repos
- [ ] postman collection
- [ ] extend tests

### SecondMs (MS)
- [ ] create project in Spring Boot and connect it with chosen API
- [ ] Add DB connection
- [ ] Try to utilize TDD

### gh-api (Client)
- [ ] Create project in Vue using primeFaces
- [ ] Implement CRUD operations for both services
- [ ] work on authentication

### Other
- [ ] api gateway
- [ ] K3s working prototype
- [ ] E2E testing


## Lessons Learned

What did you learn while building this project? What challenges did you face and how did you overcome them?

