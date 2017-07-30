Ext.define('ivp.view.Main', {
    extend : 'Ext.container.Container',
    requires : ['ivp.view.MenuView' ],

    xtype : 'app-main',

    layout : {
        type : 'border'
    },

    initComponent : function() {
        var me = this;

        Ext.applyIf(me, {

            items : [{
                region : 'north',
                height : 72,
                xtype : 'panel',
                // split : true,
                html : '',
                width : '100%',
                layout : 'hbox',
                items : [{
                    xtype : 'panel',
                    height : 72,
                    border : 0,
                    width : 1381,
                    bodyStyle : 'background : url(resources/images/maintop.jpg) no-repeat',
                }, {
                    xtype : 'panel',
                    height : 72,
                    border : 0,
                    bodyStyle : 'background : url(resources/images/maintoprepeat.jpg) repeat',
                    // width : '100%',
                    flex : 1
                }]

            }, {
                region : 'west',
                xtype : 'menuview',
                title : '菜单',
                width : 200,
                split : true
                
                
            // items : [{
            // xtype : 'button',
            // // text : '添加车辆',//
            // text : '查看地图',
            // listeners : {
            // click : {
            // fn : me.onDoneButtonClick,
            // scope : me
            // }
            // }
            //
            // }]
            }, {
                region : 'center',
                xtype : 'tabpanel',
                items : [{
                    title : '首页'
                }]
            }]
        });

        me.callParent(arguments);
    },

    onDoneButtonClick : function(button, e, eOpts) {
        // Ext.Ajax.request({
        // url : "v/save",
        // params : {
        // 'node' : 'Hello'
        // },
        // method : 'POST',
        // success : function(response, opts) {
        // console.log('=========== success response ',response);
        // },
        // failure : function(response, opts) {
        // console.log('=========== failure response ',response);
        // }
        // });
        var win = window.open("map.html", "csMap");
        win.focus();
    }
});