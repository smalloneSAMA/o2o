$(function () {

    var listUrl = '/o2o_war_exploded/shopadmin/getproductcategorylist';
    var addUrl = '/o2o_war_exploded/shopadmin/addproductcategories';
    var deleteUrl = '/o2o_war_exploded/shopadmin/removeproductcategory';

/*
    getProductCategoryList();
    function getProductCategoryList() {
        $.ajax({
            url:"/o2o_war_exploded/shopadmin/getproductcategorylist",
            type: "get",
            dataType: "json",
            success: function (data) {
                if(data.success){
                    // handleList(data.productcategorylist);
                    handleList(data.data);
                }
            }
        });
    }

    function handleList(data) {
        html = "";
        data.map(function (item,index) {
            html += '<div class="row row-shop"><div class="col-40">'
                + item.productCategoryName+ '</div><div class="col-40">'
                + item.priority
                + '</div>'
                + '<div class="col-20"><a href="#" class="button delete" data-id="'
                + item.productCategoryId
                + '">删除</a></div></div>';
        });
        $('.product-categroy-wrap').html(html);
    }
*/

    // 调用getProductCategoryList，加载数据
    getProductCategoryList();

    function getProductCategoryList() {
        $.getJSON(
            listUrl,
            function(data) {
                if (data.success) {
                    var dataList = data.data;
                    $('.product-category-wrap').html('');
                    var tempHtml = '';
                    dataList
                        .map(function(item, index) {
                            tempHtml += ''
                                + '<div class="row row-product-category now">'
                                + '<div class="col-40 product-category-name">'
                                + item.productCategoryName
                                + '</div>'
                                + '<div class="col-40">'
                                + item.priority
                                + '</div>'
                                + '<div class="col-20"><a href="#" class="button delete" data-id="'
                                + item.productCategoryId
                                + '">删除</a></div>'
                                + '</div>';
                        });
                    $('.product-category-wrap').append(tempHtml);
                }
            });
    }

    // 新增按钮的点击事件
    $('#new')
        .click(
            function () {
                // 新增数据 以 temp 为标识，便于和库表中的数据区分开来
                var tempHtml = '<div class="row row-product-category temp">'
                    + '<div class="col-40"><input class="category-input category" type="text" placeholder="分类名"></div>'
                    + '<div class="col-40"><input class="category-input priority" type="number" placeholder="优先级"></div>'
                    + '<div class="col-20"><a href="#" class="button delete">删除</a></div>'
                    + '</div>';
                $('.product-category-wrap').append(tempHtml);
    });


    $('#submit').click(function() {
        // 通过temp 获取新增的行
        var tempArr = $('.temp');
        // 定义数组接收新增的数据
        var productCategoryList = [];
        tempArr.map(function(index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url : addUrl,
            type : 'POST',
            // 后端通过 @HttpRequestBody直接接收
            data : JSON.stringify(productCategoryList),
            contentType : 'application/json',
            success : function(data) {
                if (data.success) {
                    $.toast('新增【' + data.effectNum + '】条成功！');
                    // 重新加载数据
                    getProductCategoryList();
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
    });

    // 一种是需要提交到后台的删除 now ，另外一种是 新增但未提交到数据库中的删除 temp

    $('.product-category-wrap').on('click',
        '.row-product-category.now .delete', function(e) {
            var target = e.currentTarget;
            $.confirm('确定么?', function() {
                $.ajax({
                    url : deleteUrl,
                    type : 'POST',
                    data : {
                        productCategoryId : target.dataset.id,
                    },
                    dataType : 'json',
                    success : function(data) {
                        if (data.success) {
                            $.toast('删除成功！');
                            // 重新加载数据
                            getProductCategoryList();
                        } else {
                            $.toast('删除失败！');
                        }
                    }
                });
            });
        });

    $('.product-category-wrap').on('click',
        '.row-product-category.temp .delete', function(e) {
            $(this).parent().parent().remove();
        });
});