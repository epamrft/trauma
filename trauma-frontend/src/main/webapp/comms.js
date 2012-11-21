var BackendComms = Class.create({

  initialize : function() {
    var url = 'http://trauma.backend.cloudfoundry.com/markers';
  },


  sendMarker : function () {    
      
      new Ajax.Request(this.url, {
      method: 'post',
      contentType: 'application/json',
      postBody: '{ longitude: ' + document.getElementById("lng").innerHTML + ', latitude: ' + document.getElementById("lat").innerHTML + ', desc: ' + document.getElementById("descfield").value +'}',  
      onSuccess: function(transport) {
        console.log(transport);
        }

      });​

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


  getMatkers : function (argument) {
    
  }


});