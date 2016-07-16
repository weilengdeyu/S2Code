$(function(){
	
	var marginTop = 0;

    function startsetInterval() {
        var stop = false;
        //----------书讯快递的滚动
        setInterval(function () {
            if (stop) {
                return;
            }
            $("#express").children("li").first().animate({ "margin-top": marginTop-- }, 0, function () {
                var $first = $(this);
                if (!$first.is(":animated")) {

                    if ((-marginTop) > $first.height()) {
                        $first.css({ "margin-top": 0 }).appendTo($("#express"));
                        marginTop = 0;

                    }
                }
            });
        }, 100);
        $("#express").hover(function () {
            //startsetInterval(true);
            stop = true;
        }, function () {
            // startsetInterval(false);
            stop = false;
        });
    }
    startsetInterval();


});