///////////////////////////////////////////////////////////////////////////
// A React Like Functional HTML Library that supports stateless and stateful 
// components as well as component composition in less than 20 lines
//

// Got from Jean-Jacques Dubray in https://medium.com/@metapgmr/hex-a-no-framework-approach-to-building-modern-web-apps-e43f74190b9c
let render = function (component, initState = {}, mountNode = 'app') {
  initState.render = function( stateRepresentation, options = {} ) {
    const start = (options.focus) ? document.getElementById(options.focus).selectionStart : 0;
    (document.getElementById(mountNode) || {}).innerHTML = stateRepresentation
    if (options.focus) {
      let f = document.getElementById(options.focus)
      f.selectionStart = start
      f.focus()
    }
  }

  let stateRepresentation = component(initState)

  initState.render((typeof stateRepresentation === 'function' ) ? stateRepresentation() : stateRepresentation)
}

let intent = function(i, f) {
  window[i || '_'] = f 
}

let value = function(el) {
  return document.getElementById(el).value
}

export default render;