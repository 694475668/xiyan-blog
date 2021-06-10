'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

// BASE_API: '"http://192.168.1.171:8085"'
module.exports = merge(prodEnv, {
    NODE_ENV: '"development"',
    BASE_API: '"http://127.0.0.1:9000"'
})