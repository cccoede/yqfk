module.exports = {
    devServer: {
        proxy: {
            '/api': {                                       // /api表示拦截以/api 开头的请求路径
                target: 'http://localhost:8888/yqfk',   //跨域的域名
                changeOrigin: true,                         // 是否允许跨域
                ws: true,                                   // 是否代理websocket
                pathRewrite: {
                    '^/api': ''                             //把 /api变成空字符串
                }
            },
            '/uploads': {                                       // /api表示拦截以/api 开头的请求路径
                target: 'http://localhost:8888/yqfk',   //跨域的域名
                changeOrigin: true,                         // 是否允许跨域
                ws: true,                                   // 是否代理websocket
              
            }
        }
    }
}