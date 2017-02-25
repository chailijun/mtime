self.onload = function () {
    var img_template = '<img src="#{img_src}"/><em class="em"></em>';
    var img_nomore_template = '<img src="#{img_src}"/>';
    var img_nodes = document.body.getElementsByTagName('img');
    var imgs = [];
    for (var i = 0, l = img_nodes.length; i < l; ++i) {
        if (img_nodes.item(i).id != "imgDot") {
            img_nodes.item(i).setAttribute("preload","none");//取消预加载
            imgs.push(img_nodes.item(i));
        }
    }

    for (var i = 0, l = imgs.length; i < l; ++i) {
        var img = imgs[i];
        var src = img.src;
        var template = img.width > 200 ? img_template : img_nomore_template;

        var new_img_container = document.createElement('div');
        imgs[i].parentNode.replaceChild(new_img_container, imgs[i]);

        new_img_container.innerHTML = template.replace(/#{img_src}/g, src);
        new_img_container.className = 'img_boxs';
        new_img_container.setAttribute("url", src);
        new_img_container.addEventListener('click', function () {
            var url = this.getAttribute("url");
//            location.href = url;
            javascript:android.showImage(url);
        }, false);
    }

//    var video_template = '<video src="#{video_src}"></video>';
    var video_template = '<a rel="#{video_src}"><img src="#{img_src}"><i></i></a>';
    var video_nodes = document.body.getElementsByTagName('video');
    var videos = [];
    for (var i = 0, l = video_nodes.length; i < l; ++i) {
        video_nodes.item(i).setAttribute("preload","none");//取消预加载
        videos.push(video_nodes.item(i));
    }

    for (var i = 0, l = videos.length; i < l; ++i) {
        var src = videos[i].src;
        var videoImg = videos[i].poster;
        var new_video_node = document.createElement('div');
        videos[i].parentNode.replaceChild(new_video_node, videos[i]);
        new_video_node.innerHTML = video_template.replace('#{video_src}', src).replace(/#{img_src}/g,videoImg);
        new_video_node.className = 'video_boxs';

        new_video_node.setAttribute("url", src);
        new_video_node.addEventListener('click', function () {
            var url = this.getAttribute("url");
//            location.href = url;
            javascript:android.playVideo(url);
        }, false);

    }
}


