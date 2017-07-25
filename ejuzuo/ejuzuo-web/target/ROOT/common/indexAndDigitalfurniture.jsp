<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- nav -->
<div class="nav">
  <div class="main">
    <ul class="nav_list clearfix">
      <li class="navLi alltypeLi pull_list">
        <a href="#" class="navBlock">全部商品分类</a>
        <ul class="all_type_list" style="display:block" id="typeList">

        </ul>
      </li>
      <li class="navLi"><a href="/index" class="navBlock">首页</a></li>
      <li class="navLi"><a href="/digital/index" class="navBlock">数字家居</a></li>
      <li class="navLi pull_list js_pull_list">
        <a href="/designer/queryDesigner/3/0/0" class="navBlock">设计师</a>
        <ul class="nav_pull_list">
          <li><a href="/designer/queryDesigner/3/0/0" target="_blank">个人设计师</a></li>
          <li><a href="/designer/queryDesigner/2/0/0" target="_blank">装修公司</a></li>
          <li><a href="/designer/queryDesigner/1/0/0" target="_blank">设计公司</a></li>
        </ul>
      </li>
      <li class="navLi"><a href="/activity/index" class="navBlock">活动</a></li>
      <li class="navLi"><a href="/newsInfo/index" class="navBlock">新闻资讯</a></li>
    </ul>
  </div>
</div><!-- nav END -->


<script type="text/template" id="dotTemplate_index">
  <li class="alltype_aLi" style="height:56px">
      <i class="all_type_icon {{=it.bz || 'all_type_icon_kt'}}"></i>
      <div class="txtbox">
        <p class="typeName"><a href="/digital/digitalList/{{=it.valueCode}}/0/0/0">{{=it.valueName}}</a></p>
        <p>
          {{~it.subList :value:index}}
            {{? index < 3}}
            <a href="/digital/digitalList/{{=it.valueCode}}/{{=value.valueCode}}/0/0" target="_blank">{{=value.valueName}}</a>
            {{?}}
          {{~}}
        </p>
      </div>
      <div class="service_float">
        <div class="service_float_item">
          <div class="service_fi_links">
           <div class="title_box"><a href="/digital/digitalList/{{=it.valueCode}}/0/0/0" class="more_link fr">{{=it.valueName}} {{=it.totalCount}}件商品》</a><span class="title_link">{{=it.valueName}}》</span></div>
            <ul class="tablink_list clearfix">
              {{~it.subList :value:index}}
              <li><a href="/digital/digitalList/{{=it.valueCode}}/{{=value.valueCode}}/0/0" target="_blank">{{=value.valueName}}</a></li>
              {{~}}
            </ul>
             <div class="type_r_img_box clearfix">
              {{~it.extension :value:index}}
                 {{? index == 0}}
                 <div class="type_r_img type_r_img_left" >
                 {{??}}
                     <div class="type_r_img " >
                 {{?}}
                     <a href="/digital/digitalList/{{=it.valueCode}}/0/0/{{=value.id}}"><img src="http://r.ejuzuo.com{{=value.img}}" alt=""></a>
                 </div>
              {{~}}
             </div>
           </div>
       </div>
     </div>
    </div>
  </li>
</script>
<script type="text/javascript">
  $(document).ready(function () {
      var navigation = function(){
          $('.js_pull_list').mouseover(function(){
              $(this).addClass('hover');
          });
          $('.js_pull_list').mouseout(function(event) {
              $(this).removeClass('hover');
          });
          var timer = null;
          $('.alltypeLi').mouseover(function(event) {
              clearTimeout(timer);
              $('.all_type_list').addClass('show');
          });
          $('.alltypeLi').mouseout(function(event){
              timer = setTimeout(function(){
                  $('.all_type_list').removeClass('show');
              },1)
          })
          $('.alltype_aLi').mouseover(function(event) {
              clearTimeout(timer);
              $(this).addClass('hover').siblings().removeClass('hover');
          });
          $('.alltype_aLi').mouseout(function(event) {
              event.stopPropagation();
              $(this).removeClass('hover');
              timer = setTimeout(function(){
                  $('.all_type_list').removeClass('show');
              },1)
          });
      };


    $.getJSON('/index/navigation', function(data){
      for(var i=0; i<data.length; i++) {
          var tmpl = document.getElementById('dotTemplate_index').innerHTML;
          var doTtmpl = doT.template(tmpl);
          var item = data[i];
          var dataHtml = doTtmpl(item);
          $('#typeList').append(dataHtml);
      }

      navigation();
    });
  });
</script>
