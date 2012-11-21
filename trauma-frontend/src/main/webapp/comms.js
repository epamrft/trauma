var BackendComms = Class.create({

  initialize : function() {
    
  },


  sendMarker : function () {    
      var url = 'http://trauma.backend.cloudfoundry.com/markers';

      var data = {
            longitude : document.getElementById("lng").innerHTML,
            latitude : document.getElementById("lat").innerHTML,
            desc : document.getElementById("descfield").value
        };

      new Ajax.Request(url, {
      method: 'post',
      contentType: 'application/json',
      postBody: Object.toJSON(data),  
      onSuccess: function(transport) {
        console.log(transport.responseText);
        $('response').update(transport.responseText);
        }

      });

  },


  deleteMarker : function (markerId) {

    new Ajax.Request(this.url+'/'+markerId,
      {
        method:'DELETE',

        onSuccess: function(transport){
            alert("Marker deleted successfully!");
        }

      });

  },


  updateMarker : function (markerId, desc) {
    
    new Ajax.Request(this.url+'/'+markerId,
      {
        method:'EDIT',
        parameters: '{desc: ' + desc + '}',

        onSuccess: function(transport){
            alert("Marker deleted successfully!");  
        }

      });

  },


  getMatkers : function () {

      geo_position_js.getCurrentPosition(p, function() {}, {
        enableHighAccuracy : true
      });

      var url = 'http://trauma.backend.cloudfoundry.com/markers';

      var data = {
            central-lan : p.coords.latitude,
            central-lng : p.coords.longitude,
            central-rad : 5.000
        };

      new Ajax.Request(url, {
      method: 'get',
      contentType: 'application/json',
      postBody: Object.toJSON(data),  
      onSuccess: function(transport) {
        console.log(transport.responseText);
        $('response').update(transport.responseText);
        }

      });

    
  }


});