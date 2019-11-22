$(function () {

    var listUrl = '/o2o_war_exploded/shopadmin/getproductcategorylist';
    var addUrl = '/o2o_war_exploded/shopadmin/addproductcategorys';
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
                                + '<div class="row row-product-category">'
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


});