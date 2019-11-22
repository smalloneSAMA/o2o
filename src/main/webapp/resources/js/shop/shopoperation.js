/*
1.从后台获取店铺分类等信息，填充到前台
2.将表单信息获取注册到后台
*/
$(function () {
    // 获取shopId
    var shopId = getQueryString('shopId');
    // 判断shopId是否为空
    var isEdit = shopId ? true : false;
    var initUrl = '/o2o_war_exploded/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o_war_exploded/shopadmin/registershop';
    var shopInfoUrl = "/o2o_war_exploded/shopadmin/getshopbyid?shopId=" + shopId;
    var editShopUrl =  "/o2o_war_exploded/shopadmin/modifyshop";
    // alert(initUrl);
    if(!isEdit){
        // 如果不是修改，就加载初始化信息
        getShopInitInfo();
        $('#kaptcha_img').click();
    }else {
        // 如果是修改信息，则传入shopId进行修改
        getShopInfo(shopId);
        $('#kaptcha_img').click();
    }

    // 修改店铺信息
    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function(data) {
            // 数据存在
            if (data.success) {
                var shop = data.shop;
                // 赋值 要和shop实体类中的属性名保持一致
                $('#shop-name').val(shop.shopName);
                // 店铺名称不能修改
//				$('#shop-name').attr('disabled', 'disabled');
                $('#shop-address').val(shop.shopAddress);
                $('#shop-phone').val(shop.phone);
                $('#shop-description').val(shop.shopDescription);
                // 商品目录进行赋值 商品目录仅仅加载对应的目录，且不可编辑
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                $('#shop-category').html(shopCategory);
                // 设置为不可编辑
                $('#shop-category').attr('disabled', 'disabled');

                // 区域进行赋值 区域可以进行编辑，并且初始设置为后台对应的区域
                var tempShopAreaHtml = '';
                data.areaList.map(function(item, index) {
                    tempShopAreaHtml += '<option data-id="' + item.areaId
                        + '">' + item.areaName + '</option>';
                });
                $('#area-category').html(tempShopAreaHtml);
                // 初始设置为后台对应的区域
                $("#area-category option[data-id='" + shop.area.areaId + "']")
                    .attr("selected", "selected");
            } else {
                $.toast(data.errMsg);
            }
        });

    }

    /*
     * 获取店铺初始化信息：店铺分类和区域信息列表
     */
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            // 数据存在
            if (data.success) {
                var tempHtml = "";
                var tempAreaHtml = "";
                // 迭代店铺分类列表
                data.shopCategoryList.map(function(item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId
                        + '">' + item.shopCategoryName + '</option>';
                });
                // 迭代区域信息
                data.areaList.map(function(item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area-category').html(tempAreaHtml);
            }
        });
    }


    // 表单提交
    $('#submit').click(function () {
        var shop = {};
        if(isEdit){
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddress = $('#shop-address').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDescription = $('#shop-description').val();
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#area-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var shopImg = $('#shop-img')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));
        var verifyCodeActual = $("#kaptcha_input").val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！')
            return
        }
        formData.append('verifyCodeActual', verifyCodeActual);
        $.ajax({
            url: (isEdit? editShopUrl : registerShopUrl),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success ){
                    $.toast('提交成功!');
                } else {
                    $.toast('提交失败!' + data.errMsg);
                }
                $('#kaptcha_img').click();
            }
        })
    });

})
