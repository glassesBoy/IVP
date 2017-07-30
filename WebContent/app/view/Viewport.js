Ext.define('ivp.view.Viewport', {
    extend: 'Ext.container.Viewport',
    requires:[
        'Ext.layout.container.Fit',
        'ivp.view.Main'
    ],

    layout: {
        type: 'fit'
    },

    initComponent : function() {
        var me = this;
        me.callParent(arguments);
        

        // var dtop = me.down('desktop');
        var dtop = Ext.getCmp('desktopId');
        // console.log('======dtop ', dtop);
        if (dtop) {
            // console.log('======dtop in if ');
            me.getLayout().setActiveItem('desktopId');
        } else {
            // console.log('======dtop in else ');
            var desktopApp = Ext.create("ivp.desktop.common.App", {
                //'appsStore' : resp.extraData,
                'viewport' : me
            });
            // me.add([me.desktop]);
        }
         
    }
//    items: [{
//        xtype: 'app-main'
//    }]
});
