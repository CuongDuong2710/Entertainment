var channelID = 'UC8pr9FmQ3A_dW99jqWEsveg';

$(document).ready(function(){
	$.get(
		"https://www.googleapis.com/youtube/v3/channels", {
			part: 'contentDetails',
			id: channelID,
			key: 'AIzaSyDlMX3v-eiC_SLkwuOrpvL19lRpTZbW4fI'},
			function(data){
				$.each(data.items, function(i, item){
					console.log(item);
					pid = item.contentDetails.relatedPlaylists.uploads;
					getVids(pid);
				})
			}
	);
	
	function getVids(pid){
		$.get(
		"https://www.googleapis.com/youtube/v3/playlistItems", {
			part: 'snippet',
			maxResults: 10,
			playlistId: pid,
			key: 'AIzaSyDlMX3v-eiC_SLkwuOrpvL19lRpTZbW4fI'},
			function(data){
				var output;
				$.each(data.items, function(i, item){
					console.log(item);
					videoTitle = item.snippet.title;
					videoId = item.snippet.videoId;
					
					output = '<li><iframe src=\"//www.youtube.com/embed/'+videoId+'\"></iframe></li>';
					
					// Append to results listStyleType
					$('#results').append(output);
				})
			}
		);
	}
	
});