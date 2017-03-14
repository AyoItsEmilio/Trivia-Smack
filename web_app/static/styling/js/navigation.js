$(document).ready(function(){
    $(".jumbotext").fadeIn(1700);

    $("nav a").click(function(event){
        var element = $(this).attr('href');
        if (element.charAt(0) == '#') { event.preventDefault(); }
        var position = $(element).offset().top - 105;
        $('html, body').animate({
            scrollTop: position
        }, 500);
    });

    $(window).scroll(function(){
    	if($(document).scrollTop() > 0){
    		$("nav").addClass("short-nav");
    	}
    	else
    	{
			$("nav").removeClass("short-nav");
    	}
    	if($(document).scrollTop() > $("#about").offset().top - 110){
    		$("nav a.about").addClass("current-nav");
    		$("nav a.projects").removeClass("current-nav");
    		$("nav a.resumenav").removeClass("current-nav");
    	}
    	else
    	{
    		$("nav a").removeClass("current-nav");
    	}
    	if($(document).scrollTop() > $("#skills").offset().top - 110){
    		$("nav a.projects").addClass("current-nav");
    		$("nav a.about").removeClass("current-nav");
    	}
    	if($(document).scrollTop() > $("#skills").offset().top - 110){
    		$("nav a.projects").addClass("current-nav");
    		$("nav a.about").removeClass("current-nav");
    	}
    	if($(window).scrollTop() + $(window).height() == $(document).height()){
    		$("nav a.resumenav").addClass("current-nav");
    		$("nav a.projects").removeClass("current-nav");
    	}
    });
});
