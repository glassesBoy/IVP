/**
 * 轨迹
 */
Ext.define('ivp.view.track.TrackForm', {
    extend : 'Ext.window.Window',
    alias : 'widget.trackform',
    itemId : 'trackformId',

    // title : ' ',
    // modal : true,
    constrainHeader : true,

    // autoShow : true,
    height : 600,
    width : 1000,
    maximizable : true,
    // minimizable : true,
    layout : 'fit',

    initComponent : function() {
        var me = this;

        Ext.applyIf(me, {
            items : [{
                xtype : "component",
                itemId : 'ifram',
                width : '100%',
                height : '100%',
                autoRefresh : true,
                autoEl : {
                    tag : "iframe",
                    id : "mapFrame",
                    src : "track.html?tvID=" + me.tvID + "&tms=" + me.tractTimes
                }

            // }],
            // dockedItems : [{
            // xtype : 'toolbar',
            // dock : 'top',
            // items : [{
            // text : '运行轨迹',
            // handler : me.startViewTrack
            // }]
            }]
        // listeners : {
        // minimize : {
        // fn : me.onWindowMinimize,
        // scope : me
        // }
        // }
        });
        me.callParent(arguments);
    },
    // onWindowMinimize : function(win, eOpts) {
    // win.minimize();
    // }

    startViewTrack : function(btn) {
        // console.log('========= document.lushu ', document.lushu);

        // var mapview = btn.up('window').down('#ifram');
        // console.log('============= mapview ',mapview);
        console.log('================== window ', window);
        var lush = window.frames['mapFrame'].document.lushu;
        console.log('=============  lush ', lush);

        lushu.start();
    }

});
