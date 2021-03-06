$(function() {
    var loading = false;
    // 分页允许返回的最大条数，超过此数值，禁止访问后台
    var maxItems = 999;
    // 一页返回的最大条数
    var pageSize = 3;
    var listUrl = '/o2o_war_exploded/frontend/listshops';
    var searchDivUrl = '/o2o_war_exploded/frontend/listshopspageinfo';
    // 页码
    var pageNum = 1;
    var parentId = getQueryString('parentId');
    var areaId = '';
    var shopCategoryId = '';
    var shopName = '';

    // 加载店铺列表以及区域列表
    getSearchDivData();
    // 预先加载pageSize *pageNum 条
    addItems(pageSize, pageNum);

    function getSearchDivData() {
        var url = searchDivUrl + '?parentId=' + parentId;
        $.getJSON(url,
            function(data) {
                if (data.success) {
                    var shopCategoryList = data.shopCategoryList;
                    var html = '';
                    html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                    // 店铺列表 拼接a标签集
                    shopCategoryList
                        .map(function(item, index) {
                            html += '<a href="#" class="button" data-category-id='
                                + item.shopCategoryId
                                + '>'
                                + item.shopCategoryName
                                + '</a>';
                        });
                    // 将拼接好的类别标签嵌入前台的html组件里
                    $('#shoplist-search-div').html(html);
                    var selectOptions = '<option value="">全部区域</option>';
                    // 获取后台返回的区域列表信息
                    var areaList = data.areaList;
                    // 区域列表
                    areaList.map(function(item, index) {
                        selectOptions += '<option value="'
                            + item.areaId + '">'
                            + item.areaName + '</option>';
                    });
                    $('#area-search').html(selectOptions);
                }
            });
    }

    /**
     * 获取分页店铺列表信息
     */
    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&areaId=' + areaId
            + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        // 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
        loading = true;
        // 访问后台获取相应查询条件下的店铺列表
        $.getJSON(url, function(data) {
            if (data.success) {
                // 店铺总数
                maxItems = data.count;
                var html = '';
                // 遍历店铺列表
                data.shopList.map(function(item, index) {
                    html += '' + '<div class="card" data-shop-id="'
                        + item.shopId + '">' + '<div class="card-header">'
                        + item.shopName + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.shopImg + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.shopDescription
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                // 目前显示的卡片总数
                var total = $('.list-div .card').length;
                // 若总数达到跟按照此查询条件列出来的总数一直，则停止后台的加载
                if (total >= maxItems) {
                    // 加载完毕，注销无限加载时间，防止不必要的加载
                    $('.infinite-scroll-preloader').hide();
                }else{
                    $('.infinite-scroll-preloader').show();
                }
                // 页码加1继续load新店铺
                pageNum += 1;
                // 加载结束，可以再次加载
                loading = false;
                // 刷新页面,显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕 自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    // 店铺详情页面
    $('.shop-list').on('click', '.card', function(e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o_war_exploded/frontend/shopdetail?shopId=' + shopId;
    });

    $('#shoplist-search-div').on(
        'click',
        '.button',
        function(e) {
            if (parentId) {// 如果传递过来的是一个父类下的子类
                shopCategoryId = e.target.dataset.categoryId;
                // 若之前已经选定了别的category，则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    shopCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                // 由于查询条件改变，清空店铺查询框
                $('.list-div').empty();
                // 重置页码
                pageNum = 1;
                addItems(pageSize, pageNum);
            } else {// 如果传递过来的父类为空，则按照父类查询
                parentId = e.target.dataset.categoryId;
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    parentId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                // 由于查询条件改变，清空店铺查询框
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
                parentId = '';
            }

        });

    // 需要查询的店铺名字发生变化后，重置页码，清空原先的店铺里诶包，按照新的名字查询
    $('#search').on('change', function(e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 需要查询的区域信息发生变化后，重置页码，清空原先的店铺里诶包，按照新的名字查询
    $('#area-search').on('change', function() {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 点击打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-left-demo');
    });
    // 初始化页面
    $.init();
});
