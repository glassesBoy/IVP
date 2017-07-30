/**
 * 测试车辆信息
 */
Ext.define('ivp.view.gpsdata.TestingVehicleView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.testingvehicleview',
    itemId : 'testingvehicleviewId',

    init : function() {
        this.launcher = {
            text : '测试车辆信息',
            iconCls : 'icon-grid'
        };
    },
    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('testingvehicleviewId');
        if (!win) {

            var testingVehicleStore = Ext.create('Ext.data.Store', {
                fields : ['id', 'vehicleID', 'vid', 'vName', 'vCode', 'vLength', 'vWidth', 'gpsEID', 'gpseid', 'gpsEName', 'eName', 'gpsECode', 'eCode', 'testedTimes', 'testing',
                        'oid', 'oName', 'x', 'y'],

                autoLoad : true,
                proxy : {
                    type : 'ajax',
                    url : 's/tstv/s',
                    reader : {
                        type : "json",
                        root : "data",
                        successProperty : 'success',
                        totalProperty : 'total'
                    }
                }
            });

            win = desktop.createWindow({
                id : 'testingvehicleviewId',
                title : '测试车辆信息',
                iconCls : 'icon-grid',
                animCollapse : true,
                constrainHeader : true,
                layout : 'border',

                height : 500,
                width : 900,
                items : [{
                    xtype : 'gridpanel',
                    region : 'center',
                    stripeRows : true,
                    columnLines : true,
                    store : testingVehicleStore,
                    split : true,
                    columns : [{
                        // xtype : 'gridcolumn',
                        // dataIndex : 'teamName',
                        // text : '车队名称'
                        // }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vName',
                        text : '测试车辆名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vCode',
                        text : '车辆号码'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vLength',
                        text : '车辆长度'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vWidth',
                        text : '车辆宽度'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'gpsEName',
                        text : 'GPS设备名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'gpsECode',
                        text : 'GPS设备编号'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'testedTimes',
                        text : '比赛次数'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'testing',
                        text : '状态',
                        renderer : function(v, m, r) {
                            if (v == true || v == 'true') {
                                return '测试中';
                            } else {
                                return '待测试';
                            }
                        }
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'oName',
                        text : '障碍物名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'x',
                        text : '经度'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'y',
                        text : '纬度'

                    }],
                    dockedItems : [{
                        xtype : 'pagingtoolbar',
                        store : testingVehicleStore,
                        dock : 'bottom',
                        displayInfo : true
                    }, {
                        xtype : 'toolbar',
                        dock : 'top',
                        items : [{
                            text : '添加测试车辆',
                            handler : me.addTestingVehicle
                        }, '-', {
                            text : '删除',
                            handler : me.deleteTestingVehicle
                        }, '-', {
                            text : '开始测试',
                            handler : me.startTestingVehicle
                        }, '-', {
                            text : '停止测试',
                            handler : me.stopTestingVehicle
                        }]
                    }],
                    listeners : {
                        itemdblclick : {
                            fn : me.onUpdateTestingVehicle,
                            scope : me
                        }
                    }

                }]
            });
        }
        return win;
    },

    addTestingVehicle : function(btn) {
        var grid = btn.up('grid');
        var gridStore = grid.getStore();
        var createWin = Ext.create('ivp.view.gpsdata.TestingVehicleForm', {
            gridStore : gridStore,
            title : '新建测试车辆',
            udt : false
        });
        createWin.show();
    },
    
    deleteTestingVehicle : function(btn) {
        var grid = btn.up('grid');
        var record = grid.getSelectionModel().getSelection()[0];
        if (record) {
        } else {
            Ext.MessageBox.alert('', '您好像没有选择任何信息');
            return false
        }

        // console.log('==================record ', record);
        Ext.Msg.confirm("信息", "确定要删除吗？", function(button) {
            if (button == "no") {
                return false;
            }
            var gridStore = grid.getStore();

            var vtID = record.data.id;

            Ext.Ajax.request({
                clientValidation : true,
                url : 's/tstv/dlt',
                params : {
                    "id" : vtID
                },
                success : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('成功', resp.message);
                    gridStore.load();

                },
                failure : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('失败', resp.message);
                }
            });
        });
    },

    onUpdateTestingVehicle : function(grid, record, index, eOpts) {
        var gridStore = grid.getStore();
        var updateWin = Ext.create('ivp.view.gpsdata.TestingVehicleForm', {
            gridStore : gridStore,
            title : '修改测试车辆',
            udt : true
        });
        updateWin.down('form').getForm().setValues(record.data);
        updateWin.show();

    },

    startTestingVehicle : function(btn) {
        var grid = btn.up('grid');
        var record = grid.getSelectionModel().getSelection()[0];
        if (record) {
        } else {
            Ext.MessageBox.alert('', '您好像没有选择任何信息');
            return false
        }

        if (record.data.testing == true || record.data.testing == 'true') {
            Ext.MessageBox.alert('', '该车辆已经开始测试');
            return false
        }

        var gridStore = grid.getStore();

        var vtID = record.data.id;

        Ext.Ajax.request({
            clientValidation : true,
            url : 's/tstv/start',
            params : {
                "id" : vtID
            },
            success : function(response, options) {
                var resp = Ext.decode(response.responseText);
                Ext.Msg.alert('成功', resp.message);
                gridStore.load();
            },
            failure : function(response, options) {
                var resp = Ext.decode(response.responseText);
                Ext.Msg.alert('失败', resp.message);
            }
        });

    },

    stopTestingVehicle : function(btn) {
        var grid = btn.up('grid');
        var record = grid.getSelectionModel().getSelection()[0];
        if (record) {
        } else {
            Ext.MessageBox.alert('', '您好像没有选择任何信息');
            return false
        }

        if (record.data.testing == true || record.data.testing == 'true') {
        } else {
            Ext.MessageBox.alert('', '该车辆不需要停止');
            return false
        }

        var gridStore = grid.getStore();

        var vtID = record.data.id;

        Ext.Ajax.request({
            clientValidation : true,
            url : 's/tstv/stop',
            params : {
                "id" : vtID
            },
            success : function(response, options) {
                var resp = Ext.decode(response.responseText);
                Ext.Msg.alert('成功', resp.message);
                gridStore.load();
            },
            failure : function(response, options) {
                var resp = Ext.decode(response.responseText);
                Ext.Msg.alert('失败', resp.message);
            }
        });
    }

});
