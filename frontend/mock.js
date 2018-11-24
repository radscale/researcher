const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('mock-db.json')
const middlewares = jsonServer.defaults()

server.use(middlewares)

const userData = {
    "id": 1,
    "email": "test@researcher.com",
    "firstName": "Test",
    "lastName": "User",
    "eng": true,
    "bsc": true,
    "msc": false,
    "phd": false,
    "prof": false
}

server.use((req, res, next) => {
    res.set({
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
        'Access-Control-Allow-Headers': 'Authorization',
        'Access-Control-Expose-Headers': 'Authorization'
    })
    next()
})

server.post('/auth/login', (req, res) => {
    res.set({
        'Authorization': 'Bearer t3stT0k3n'
    })
    res.send('Gotcha')
})
server.get('/auth/user', (req, res) => {
    res.jsonp({
        data: userData
    })
})
server.get('/auth/refresh', (req, res) => {
    res.set({
        'Authorization': 'Bearer t3stT0k3n'
    })
    res.jsonp(userData)
})

server.use(router)
server.listen(3000, () => {
    console.log('Researcher mock data server is running.')
})