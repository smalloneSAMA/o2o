$(function() {
    var loading = false;
    // 最大数量
    var maxItems = 20;
    // 默认一页条数
    var pageSize = 3;
    // 列数商品列表的URL
    var listUrl = '/o2o_war_exploded/frontend/listproductbyshop';
    // 初始页码数
    var pageNum = 1;
    // 获取shopId
    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';
    // 获取本店信息以及商品类别信息列表的URL
    var searchDivUrl = '/o2o_war_exploded/frontend/listshopdetailpageinfo?shopId=' + shopId;
    // 渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    // 预先加载商品信息
    addItems(pageSize, pageNum);

    // 给兑换礼品的a标签赋值兑换礼品的URL，2.0版本
    // $('#exchangelist').attr('href','/o2o_war_exploded/frontend/awardlist?shopId=' + shopId);

    // 获取本店信息以及商品类别信息
    function getSearchDivData() {
        var url = searchDivUrl;
        $.getJSON(url,
            function(data) {
                if (data.success) {
                    var shop = data.shop;
                    $('#shop-cover-pic').attr('src', shop.shopImg);
                    $('#shop-update-time').html(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
                    $('#shop-name').html(shop.shopName);
                    $('#shop-description').html(shop.shopDescription);
                    $('#shop-address').html(shop.shopAddress);
                    $('#shop-phone').html(shop.phone);

                    // 店铺类别列表
                    var productCategoryList = data.productCategoryList;
                    var html = '';
                    // 遍历商品列表，生成可以点击搜索相应商品类别下的商品的a标签
                    productCategoryList
                        .map(function(item, index) {
                            html += '<a href="#" class="button" data-product-search-id='
                                + item.productCategoryId
                                + '>'
                                + item.productCategoryName
                                + '</a>';
                        });
                    $('#shopdetail-button-div').html(html);
                }
            });
    }

    /**
     * 获取分页展示的商品列表信息
     */
    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        // 设定加载符，若还在后台去数据则不能再次访问后台，避免多次重复加载
        loading = true;
        // 访问后台获取相应查询条件下的商品列表
        $.getJSON(url, function(data) {
            if (data.success) {
                // 获取当前查询条件下的商品总数
                maxItems = data.count;
                var html = '';
                // 遍历商品列表，拼接初卡片列表
                data.productList.map(function(item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.imgAddress + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDescription
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 隐藏加载提示符
                    $('.infinite-scroll-preloader').hide();
                }else{
                    $('.infinite-scroll-preloader').show();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    // 选择新商品类别，充值页码，清空原先商品列表
    $('#shopdetail-button-div').on(
        'click',
        '.button',
        function(e) {
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });

    // 商品详情页码
    $('.list-div')
        .on('click',
            '.card',
            function(e) {
                var productId = e.currentTarget.dataset.productId;
                window.location.href = '/o2o_war_exploded/frontend/productdetail?productId='
                    + productId;
            });

    // 需要查询的商品名发生变化后，重置页码，清空原先的商品列表，按照新的商品名去查询
    $('#search').on('change', function(e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#me').click(function() {
        $.openPanel('#panel-left-demo');
    });

    // 初始化页面
    $.init();
});
