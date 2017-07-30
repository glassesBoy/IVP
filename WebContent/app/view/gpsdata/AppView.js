/**
 * GPS信息 主窗口
 */
Ext.define('ivp.view.gpsdata.AppView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.gpsappview',
    itemId : 'gpsappviewId',

    init : function() {
        // me = this;
        this.launcher = {
            text : 'GPS信息管理',
            iconCls : 'icon-grid'

        };
    },
    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('gpsappviewId');
        if (!win) {
            win = desktop.createWindow({
                xtype : 'panel',
                id : 'gpsappviewId',
                title : 'GPS信息管理',
                iconCls : 'icon-grid',
                animCollapse : true,
                constrainHeader : true,
                layout : 'fit',

                height : 300,
                width : 500,
                // items : [{
                // xtype : 'panel',
                // // region : 'center',
                // layout : 'fit',
                items : [{
                    xtype : 'dataview',
                    autoScroll : true,
                    id : 'gps-chooser-view',
                    singleSelect : true,
                    itemSelector : 'div.app-icon',
                    // itemSelector: 'div.app-icon-wrap',
                    // itemTpl: [
                    // 'Data View Item {name}'
                    // ],
                    tpl : new Ext.XTemplate('<tpl for=".">', '<div class="app-icon-wrap">', '<div class="app-icon">',
                            (!Ext.isIE6 ? '<img src="resources/apps/gpsdata/{imgsrc}" />' : '<div style="width:74px;height:74px;'
                                    + 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'resources/apps/gpsdata/{imgsrc}\')"></div>'), '</div>',
                            '<span style="text-align: center;" >{name}</span>', '</div>', '</tpl>'),

                    // initComponent: function() {
                    // this.store = Ext.create('Ext.data.Store', {
                    store : Ext.create('Ext.data.Store', {
                        autoLoad : true,
                        fields : ['name', 'module', 'classpath', 'imgsrc', 'type'],
                        // proxy : {
                        // type : 'ajax',
                        // url : 's/gps/mu',
                        // extraParams : {
                        // "pcd" : "dt_gpsdata_management"
                        // },
                        // reader : {
                        // type : "json",
                        // root : "data",
                        // successProperty : 'success'
                        // }
                        // }
                        data : [{
                            name : 'GPS设备管理',
                            imgsrc : 'gpsdata1.png',
                            module : 'equipmentview',
                            classpath : 'ivp.view.gpsdata.EquipmentView',
                            type : 'Application'
                        }, {
                            name : '障碍物管理',
                            imgsrc : 'gpsdata1.png',
                            module : 'obstacleview',
                            classpath : 'ivp.view.gpsdata.ObstacleView',
                            type : 'Application'
                        }, {
                            name : '比赛车辆信息',
                            imgsrc : 'gpsdata2.png',
                            module : 'testingvehicleview',
                            classpath : 'ivp.view.gpsdata.TestingVehicleView',
                            type : 'Application'
                        }, {
                            name : 'GPS信息导入',
                            imgsrc : 'gpsdata3.png',
                            module : 'gpsdataimportview',
                            classpath : 'ivp.view.gpsdata.GPSDataImportView',
                            type : 'Application'

                        }]
                    }),// ;
                    listeners : {
                        itemclick : {
                            fn : me.onIconClick,
                            scope : me
                        }
                    }
                }]
            });
        }
        return win;
    },

    onIconClick : function(dataview, record, item, index, e, eOpts) {
        var me = this;

        var moduleApp = null;
        if (record.data.module) {
            moduleApp = me.app.getModule(record.data.module);
            if (moduleApp) {
            } else {
                moduleApp = Ext.create(record.data.classpath);
                moduleApp.app = me.app;
            }
        } else {
            Ext.MessageBox.alert('', '功能暂未实现');
            return false;
        }
        // console.log('========= win ',win);

        var win = moduleApp && moduleApp.createWindow();
        if (win) {
            moduleApp.app.desktop.restoreWindow(win);
        }

    }

});