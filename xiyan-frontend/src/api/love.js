var a_idx = 0;
jQuery(document).ready(function($) {
    $("body").click(function(e) {
        var a = new Array(
            "❤Welcome❤",
            //  "❤love❤",
            //  "❤dear❤",
            //  "❤点赞❤",
            //  "❤打赏❤",
            //  "❤come on❤",
            //  "❤推荐一下❤",
            //  "❤支持一下❤",
            //  "❤关注下❤",
            //  "❤淼淼之森❤"
        );
        var $i = $("<span></span>").text(a[a_idx]);
        a_idx = (a_idx + 1) % a.length;
        var x = e.pageX,
            y = e.pageY;
        $i.css({
            "z-index": 999999999999999999999999999999999999999999999999999999999999999999999,
            top: y - 20,
            left: x,
            position: "absolute",
            "font-weight": "bold",
            color: "rgb(" +
                ~~(255 * Math.random()) +
                "," +
                ~~(255 * Math.random()) +
                "," +
                ~~(255 * Math.random()) +
                ")",
        });
        $("body").append($i);
        $i.animate({
                top: y - 180,
                opacity: 0,
            },
            1500,
            function() {
                $i.remove();
            }
        );
    });
});