
function getPath(local) {
   return "/"+ window.location.pathname.split("/").pop()+"?lang=" + local;
}
 console.log(window.location.origin)
 console.log(window.location.path)
 console.log(window.location.pathname)
 console.log(getPath("ru"))
