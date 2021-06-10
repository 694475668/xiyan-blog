import  E  from  "wangEditor"
export default ({ app }) => {
    app.router.afterEach(() => {
        //在Window原型中设置wangEditor        
        Window.prototype.wangEditor = E;
    });
}