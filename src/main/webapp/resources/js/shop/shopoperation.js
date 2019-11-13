/*
1.从后台获取店铺分类等信息，填充到前台
2.将表单信息获取注册到后台
*/
$(function () {
  var initUrl = '/o2o_war_exploded/shopadmin/getshopinitinfo';
  var registerShopUrl = '/o2o_war_exploded/shopadmin/registershop';
  // alert(initUrl);
  getShopInitInfo();
  $('#kaptcha_img').click();

    /*
     * 获取店铺初始化信息：店铺分类和区域信息列表
     */
  function getShopInitInfo(){
      $.getJSON(initUrl,function (data) {
          // 前端下拉信息获取
          if(data.success){
              var tempHtml = '';
              var tempAreaHtml = '';
              data.shopCategoryList.map(function (item,index) {
                  tempHtml += '<option data-id="' + item.shopCategoryId + '">'
                  + item.shopCategoryName + '</option>';
              });
              data.areaList.map(function (item, index) {
                  tempAreaHtml += '<option data-id="' + item.areaId + '">'
                      + item.areaName + '</option>';
              });
              $('#shop-category').html(tempHtml);
              $('#area-category').html(tempAreaHtml);
          }
      });
      // 表单提交
      $('#submit').click(function () {
          var shop = {};
          shop.shopName = $('#shop-name').val();
          shop.shopAddress = $('#shop-addr').val();
          shop.phone = $('#shop-phone').val();
          shop.shopDescription = $('#shop-description').val();
          shop.shopCategory = {
              shopCategoryId : $('#shop-category').find('option').not(function () {
                  return !this.selected;
              }).data('id')
          };
          shop.area = {
              areaId : $('#area-category').find('option').not(function () {
                  return !this.selected;
              }).data('id')
          };
          var shopImg = $('#shop-img')[0].files[0];
          var formData = new FormData();
          formData.append('shopImg',shopImg);
          formData.append('shopStr',JSON.stringify(shop));
          var verifyCodeActual = $("#kaptcha_input").val();
          if(!verifyCodeActual){
              $.toast('请输入验证码！')
              return
          }
          formData.append('verifyCodeActual',verifyCodeActual);
          $.ajax({
              url:registerShopUrl,
              type: 'POST',
              data: formData,
              contentType:false,
              processData:false,
              cache:false,
              success:function (data) {
                  if(data.success()){
                      $.toast('提交成功!');
                  }else{
                      $.toast('提交失败!'+data.errMsg);
                  }
                  $('#kaptcha_img').click();
              }
          })
      });

  }

})
