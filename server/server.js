var express = require('express') //加载模块
var app = express() //实例化之
var bodyParser = require('body-parser')
var mysql = require('mysql');



//登录
app.get('/login', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    console.log('ok');
    if (req.query.id != null  && req.query.pwd != null) {
        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();

        console.log(req.query.id);
        console.log(req.query.id)
        var sql = 'Select * from farm_users WHERE id ="' + req.param('id') + '"and pwd="'+req.param('pwd') + '"';
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {
                if (rows.length>0){
                    res.send('success!');

                }

            }
        });

    }
    // console.log(req.query.id);
    // console.log(req.query.name)


    var data = [];

//查

});


//获取农场
app.get('/getfarms', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    res.header("Access-Control-Allow-Origin", "*");
    res.header('Access-Control-Allow-Methods', 'PUT, GET, POST, DELETE, OPTIONS');
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    // res.send(map)
    console.log('ok');
        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();

        var sql = 'Select * from farm_farms ';
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {
                var data = [];

                for (var i = 0; i < rows.length; i++) {
                    data.push({
                        id: rows[i].pid,
                        title: rows[i].title,
                        product_grade: rows[i].product_grade,
                        service_grade: rows[i].service_grade,
                        place_grade: rows[i].place_grade,
                        trip_grade: rows[i].trip_grade,
                        image: rows[i].img,
                    })
                }
                var result = {
                    genus: req.query.genus,
                    evt: req.query.evt,
                    data: data
                }

                console.log(data);
                connection.end();

                return res.jsonp(result);

            }
        });


    // console.log(req.query.id);
    // console.log(req.query.name)



//查

});

app.get('/keywords', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    res.header("Access-Control-Allow-Origin", "*");
    res.header('Access-Control-Allow-Methods', 'PUT, GET, POST, DELETE, OPTIONS');
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    // res.send(map)
    console.log('ok');
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();

    var sql = 'Select * from table_keywords ';
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            console.log('[SELECT ERROR] - ', err.message);
            res.send('no')
        }
        else {
            var data = [];

            for (var i = 0; i < rows.length; i++) {
                data.push({
                    key: rows[i].key,
                    tag: rows[i].tag,
                })
            }
            var result = {
                genus: req.query.genus,
                evt: req.query.evt,
                data: data
            }

            console.log(data);
            connection.end();

            return res.jsonp(result);

        }
    });


    // console.log(req.query.id);
    // console.log(req.query.name)



//查

});



//获取相册
app.get('/getpics', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    console.log('ok');
    if (req.query.title != null) {
        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();

        var sql = 'Select * from farm_farms WHERE title ="' + req.param('title') + '"';
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {
                if (rows.length>0){
                    res.send(rows[0].pics);

                }

            }
        });

    }
    // console.log(req.query.id);
    // console.log(req.query.name)



//查

});

//获取产品信息
app.get('/getproduct', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    console.log('ok');
    if (req.query.title != null) {
        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();

        var sql = 'Select * from farm_products WHERE ptitle ="' + req.param('title') + '"';
        console.log(sql);

        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {
                if (rows.length>0){
                    var product={
                        title:rows[0].ptitle,
                        price:rows[0].price,
                        image:rows[0].pics,
                        grade:rows[0].grade,
                        info:rows[0].info,
                        like:rows[0].like,
                        farm:rows[0].farm


                    }
                    res.send(product);

                }
                else {
                    res.send('nothing');

                }

            }
        });

    }
    // console.log(req.query.id);
    // console.log(req.query.name)



//查

});
//获取产品信息
app.get('/getfarm', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    console.log('ok');
    if (req.query.title != null) {
        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();

        var sql = 'Select * from farm_farms WHERE title ="' + req.param('title') + '"';
        console.log(sql);

        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {
                if (rows.length>0){
                    var product={
                        title:rows[0].ptitle,
                        price:rows[0].price,
                        image:rows[0].pics,
                        product_grade:rows[0].product_grade,
                        service_grade:rows[0].service_grade,

                        place_grade:rows[0].place_grade,

                        trip_grade:rows[0].trip_grade,

                        info:rows[0].info,
                        location:rows[0].location,
                        phone:rows[0].phone,


                    }
                    res.send(product);

                }
                else {
                    res.send('nothing');

                }

            }
        });

    }
    // console.log(req.query.id);
    // console.log(req.query.name)



//查

});
//注册

app.get('/regist', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    console.log('ok');
    if (req.query.id != null && req.query.name != null && req.query.pwd != null) {
        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();


        var sql = 'INSERT INTO farm_users (id, name, pwd) VALUES ("' + req.query.id + '","' + req.query.name + '","' + req.query.pwd + '")';
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {

                res.send('success!');
            }
        });

    }
    console.log(req.query.id);
    console.log(req.query.name)


    var data = [];

//查

});



//获取全部收藏
app.get('/getlike', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();
if (req.query.type='farm')
{
    var sql = 'SELECT * FROM farm_like WHERE userid ="' + req.query.userid + '"';
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            // console.log('[SELECT ERROR] - ',err.message);
            res.send('no')
        }
        else {

            var data = [];

            for (var i = 0; i < rows.length; i++) {
                var product = {
                    id: rows[i].id,
                    products: rows[i].products,
                    farms: rows[i].farms,
                }
                data.push(
                    product
                );


                console.log(product.farms)


            }
            var result = {
                genus: req.query.genus,
                evt: req.query.evt,
                data: data
            }
            console.log(data);
            connection.end();

            return res.jsonp(result);
        }
    });

}

else {

    var sql = 'SELECT * FROM product_like WHERE userid ="' + req.query.userid + '"';
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            // console.log('[SELECT ERROR] - ',err.message);
            res.send('no')
        }
        else {

            var data = [];

            for (var i = 0; i < rows.length; i++) {
                var product = {
                    id: rows[i].id,
                    products: rows[i].products,
                    farms: rows[i].farms,
                }
                data.push(
                    product
                );


                console.log(product.farms)


            }
            var result = {
                genus: req.query.genus,
                evt: req.query.evt,
                data: data
            }
            console.log(data);
            connection.end();

            return res.jsonp(result);
        }
    });

}




});

//获取收藏
app.get('/islike', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();

    if (req.query.type=='farm'){

        var sql = "SELECT * FROM farm_like WHERE userid ='" + req.query.userid +"'and farmid='"+req.query.farmid+"'";

        console.log(sql);
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ',err.message);
                res.send('error')
            }
            else {

                if (rows.length>0){

                    res.send('true')

                }

                else {


                    res.send('false')
                }



            }
        });

    }
else {

        var sql = "SELECT * FROM product_like WHERE userid ='" + req.query.userid +"'and productid='"+req.query.productid+"'";

        console.log(sql);
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ',err.message);
                res.send('error')
            }
            else {

                if (rows.length>0){

                    res.send('true')

                }

                else {


                    res.send('false')
                }



            }
        });
    }

});


//添加收藏
app.get('/addlike', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();
if (req.query.type=='farm')
{    var sql = "INSERT INTO farm_like VALUES ('" + req.query.userid +"','"+req.query.farmid+"')";

    console.log(sql);
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            console.log('[SELECT ERROR] - ',err.message);
            res.send('error')
        }
        else {

            res.send('success!')


        }
    });
}

        else{
    var sql = "INSERT INTO product_like VALUES ('" + req.query.userid +"','"+req.query.productid+"')";

    console.log(sql);
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            console.log('[SELECT ERROR] - ',err.message);
            res.send('error')
        }
        else {

            res.send('success!')


        }
    });
}
});

//取消收藏
app.get('/dislike', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();

    if (req.query.type=='farm'){
        var sql = "DELETE  FROM farm_like WHERE userid ='" + req.query.userid +"'and farmid='"+req.query.farmid+"'";

    console.log(sql);
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ',err.message);
                res.send('error')
            }
            else {

                res.send('success!')


            }
        });



        }
        else {

        var sql = "DELETE  FROM product_like WHERE userid ='" + req.query.userid +"'and productid='"+req.query.productid+"'";

        console.log(sql);
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ',err.message);
                res.send('error')
            }
            else {

                res.send('success!')


            }
        });

    }

});




//添加评价
app.get('/addjudges', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
if (req.query.type=='farm') {


    console.log(req.query.userid+req.query.farmid+req.param('grade1')+req.query.grade2+req.query.grade3+req.query.grade4+req.query.word)
    if (req.query.userid!= null&&req.query.farmid!=null&&req.query.grade1!=null&&req.query.grade2!=null&&req.query.grade3!=null&&req.query.grade4!=null&&req.query.word!=null) {


        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();
        var sql = 'INSERT INTO product_judges  VALUES ("' + req.query.userid + '","' +  req.query.farmid + '","'+ req.query.grade1 +'","'+ req.query.grade2 + '","'+req.query.grade3 + '","'+req.query.grade4 + '","'+req.query.word+'")';
        console.log(sql);

        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {

                res.send('success!');
            }
        });

    }

    else {


        res.send('缺少参数!');

    }



}

else {
    console.log(req.query.userid+req.query.productid+req.param('grade1')+req.query.grade2+req.query.grade3+req.query.grade4+req.query.word)
    if (req.query.userid!= null&&req.query.productid!=null&&req.query.grade1!=null&&req.query.grade2!=null&&req.query.grade3!=null&&req.query.grade4!=null&&req.query.word!=null) {


        var connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '123456',
            database: 'farm_schema'
        });

        connection.connect();
        var sql = 'INSERT INTO product_judges  VALUES ("' + req.query.userid + '","' +  req.query.productid + '","'+ req.query.grade1 +'","'+ req.query.grade2 + '","'+req.query.grade3 + '","'+req.query.grade4 + '","'+req.query.word+'")';
        console.log(sql);

        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ', err.message);
                res.send('no')
            }
            else {

                res.send('success!');
            }
        });

    }

    else {


        res.send('缺少参数!');

    }
}


});


//删除评价
app.get('/deletejudges', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();

    if (req.query.type=='farm'){
        var sql = "DELETE  FROM farm_judges WHERE userid ='" + req.query.userid +"'and farmid='"+req.query.farmid+"'";

        console.log(sql);
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ',err.message);
                res.send('error')
            }
            else {

                res.send('success!')


            }
        });



    }
    else {

        var sql = "DELETE  FROM product_judges WHERE userid ='" + req.query.userid +"'and productid='"+req.query.productid+"'";

        console.log(sql);
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ',err.message);
                res.send('error')
            }
            else {

                res.send('success!')


            }
        });

    }

});


//获取全部收藏
app.get('/getjudge', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();
    if (req.query.type='farm')
    {
        var sql = "SELECT * FROM farm_judges WHERE farmid='"+req.query.farmid+"'";
        connection.query(sql, function (err, rows, fileds) {
            if (err) {
                console.log('[SELECT ERROR] - ',err.message);
                res.send('no')
            }
            else {

                var data = [];

                for (var i = 0; i < rows.length; i++) {
                    var judge = {
                        userid: rows[i].userid,
                        productid: rows[i].productid,
                        grade1:rows[i].grade1,
                        grade2:rows[i].grade2,
                        grade3:rows[i].grade3,
                        grade4:rows[i].grade4,
                        price:rows[i].price,
                        word:rows[i].word
                    }
                    data.push(
                        judge
                    );




                }
                var result = {
                    genus: req.query.genus,
                    evt: req.query.evt,
                    data: data
                }
                console.log(data);
                connection.end();

                return res.jsonp(result);
            }
        });

    }

    else {

        // var sql = 'SELECT * FROM product_like WHERE userid ="' + req.query.userid + '"';
        // connection.query(sql, function (err, rows, fileds) {
        //     if (err) {
        //         // console.log('[SELECT ERROR] - ',err.message);
        //         res.send('no')
        //     }
        //     else {
        //
        //         var data = [];
        //
        //         for (var i = 0; i < rows.length; i++) {
        //             var product = {
        //                 id: rows[i].id,
        //                 products: rows[i].products,
        //                 farms: rows[i].farms,
        //             }
        //             data.push(
        //                 product
        //             );
        //
        //
        //             console.log(product.farms)
        //
        //
        //         }
        //         var result = {
        //             genus: req.query.genus,
        //             evt: req.query.evt,
        //             data: data
        //         }
        //         console.log(data);
        //         connection.end();
        //
        //         return res.jsonp(result);
        //     }
        // });

    }




});










//添加产品
app.get('/updatelike/add_products', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    console.log(req.param('user'));
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });

    connection.connect();




    });





app.get('/products/type/:type', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });
    connection.connect();
    var data = [];
if (req.param('type')=="all"){

    var sql = 'SELECT * FROM farm_products';

}
else {

    var sql = 'SELECT * FROM farm_products WHERE type ="' + req.param('type') + '"';

}

//查
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            console.log('[SELECT ERROR] - ', err.message);
            res.send('no')
        }

        // console.log('--------------------------SELECT----------------------------');
        // console.log(fileds);
        // console.log('The solution is: ', rows[0].id);
        //
        // console.log('------------------------------------------------------------\n\n');
        for (var i = 0; i < rows.length; i++) {
            data.push({
                id: rows[i].pid,
                title: rows[i].ptitle,
                price: rows[i].price,
                farm: rows[i].farm,
                grade: rows[i].grade,
                pics: rows[i].pics,
                info: rows[i].info,
                like: rows[i].like,
                comments: rows[i].comments
            })
        }
        var result = {
            genus: req.query.genus,
            evt: req.query.evt,
            data: data
        }

        console.log(data);
        connection.end();

        return res.jsonp(result);
    });

});

app.get('/products/mark/:mark', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });
    connection.connect();
    var data = [];

    var sql = 'SELECT * FROM farm_products WHERE mark ="' + req.param('mark') + '"';
//查
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            console.log('[SELECT ERROR] - ', err.message);
            res.send('no')
        }

        // console.log('--------------------------SELECT----------------------------');
        // console.log(fileds);
        // console.log('The solution is: ', rows[0].id);
        //
        // console.log('------------------------------------------------------------\n\n');
        for (var i = 0; i < rows.length; i++) {
            data.push({
                id: rows[i].pid,
                title: rows[i].ptitle,
                price: rows[i].price,
                farm: rows[i].farm,
                grade: rows[i].grade,
                pics: rows[i].pics,
                info: rows[i].info,
                like: rows[i].like,
                comments: rows[i].comments
            })
        }
        var result = {
            genus: req.query.genus,
            evt: req.query.evt,
            data: data
        }

        console.log(data);
        connection.end();

        return res.jsonp(result);
    });

});


app.get('/products/farm/:farm', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: '123456',
        database: 'farm_schema'
    });
    connection.connect();
    var data = [];

    var sql = 'SELECT * FROM farm_products WHERE farm ="' + req.param('farm') + '"';
//查
    connection.query(sql, function (err, rows, fileds) {
        if (err) {
            console.log('[SELECT ERROR] - ', err.message);
            res.send('no')
        }

        // console.log('--------------------------SELECT----------------------------');
        // console.log(fileds);
        // console.log('The solution is: ', rows[0].id);
        //
        // console.log('------------------------------------------------------------\n\n');
        for (var i = 0; i < rows.length; i++) {
            data.push({
                id: rows[i].id,
                title: rows[i].ptitle,
                price: rows[i].price,
                farm: rows[i].farm,
                grade: rows[i].grade,
                pics: rows[i].pics,
                info: rows[i].info,
                like: rows[i].like,
                comments: rows[i].comments
            })
        }
        var result = {
            genus: req.query.genus,
            evt: req.query.evt,
            data: data
        }

        console.log(data);
        connection.end();

        return res.jsonp(result);
    });

});

app.get('/gettoken', bodyParser.urlencoded({extended: false}), function (req, res) { //Restful Get方法,查找整个集合资源
    res.set({'Content-Type': 'text/json', 'Encodeing': 'utf8'});
    // res.send(map)
    var qiniu = require("qiniu");

    var accessKey = 'ytgpL3RHa1sI0YBSiMQ_Blo2ip7wSKYKCazEtwvk';
    var secretKey = 'i3ySdfZRT2nOwALwvuAxktLpSOoK6vfbsuwCopbN';
    var bucket ='farm'
    var mac = new qiniu.auth.digest.Mac(accessKey, secretKey);
    var options = {
        scope: bucket,
    };
    var putPolicy = new qiniu.rs.PutPolicy(options);
    var uploadToken=putPolicy.uploadToken(mac);
res.send(uploadToken.toString())});


var server = app.listen(8081, function () {

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)

})