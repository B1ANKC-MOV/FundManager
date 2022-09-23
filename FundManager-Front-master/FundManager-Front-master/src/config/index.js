module.exports={
    devServer:{
        proxy:{
            ["/api"]:{
            target:'http://10.151.134.125:8888',
              changeOrigin:true,
                pathRewrite: {
                    ['^' + "/api"]: ''
                }
            }
        }

    }
}
