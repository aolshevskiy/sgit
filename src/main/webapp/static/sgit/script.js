function timeConversion() {
	$(function(){
		$('.time').each(function(i, o){			  
			$(o).text(new Date($(o).text()*1000).toRelativeTime());
		});
	});
}
