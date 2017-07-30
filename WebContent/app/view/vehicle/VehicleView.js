/**
 * 
 */
Ext.define('ivp.view.vehicle.VehicleView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.vehicleview',
    itemId : 'vehicleviewId',
    requires : ['ivp.model.VehicleModel'],

    init : function() {
        this.launcher = {
            text : '车辆管理',
            iconCls : 'icon-grid'
        };
    },
    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('vehicleviewId');
        if (!win) {

            var teamStore = Ext.create('Ext.data.Store', {
                fields : ['id', 'teamName', 'teamCode', 'logoPath'],
                autoLoad : true,
                proxy : {
                    type : 'ajax',
                    url : 's/vt/s',
                    reader : {
                        type : "json",
                        root : "data",
                        successProperty : 'success',
                        totalProperty : 'total'
                    }
                }
            });

            var vStore = Ext.create('Ext.data.Store', {
                model : 'ivp.model.VehicleModel',
                // autoLoad : true,
                proxy : {
                    type : 'ajax',
                    url : 's/v/s',
                    reader : {
                        type : "json",
                        root : "data",
                        successProperty : 'success',
                        totalProperty : 'total'
                    }
                }
            });

            win = desktop.createWindow({
                id : 'vehicleviewId',
                title : '车辆管理',
                iconCls : 'icon-grid',
                animCollapse : true,
                constrainHeader : true,
                layout : 'border',

                height : 500,
                width : 900,
                items : [{
                    xtype : 'gridpanel',
                    title : '车队信息',
                    region : 'west',
                    width : 200,
                    stripeRows : true,
                    columnLines : true,
                    store : teamStore,
                    split : true,
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'teamName',
                        width : 200,
                        text : '车队名称'
                    }],
                    dockedItems : [{
                        xtype : 'pagingtoolbar',
                        store : teamStore,
                        dock : 'bottom',
                        displayInfo : true
                    }, {
                        xtype : 'toolbar',
                        dock : 'top',
                        items : [{
                            text : '添加车队',
                            handler : me.addVehicleTeam
                        }, '-', {
                            text : '删除',
                            handler : me.deleteVehicleTeam
                        }]
                    }],
                    listeners : {
                        select : {
                            fn : me.onTeamSelect,
                            scope : me
                        },
                        itemdblclick : {
                            fn : me.onUpdateTeam,
                            scope : me
                        }
                    }

                }, {
                    xtype : 'gridpanel',
                    title : '车辆信息',
                    region : 'center',
                    stripeRows : true,
                    columnLines : true,
                    store : vStore,
                    columns : [{
                        xtype : 'rownumberer',
                        width : 50
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vName',
                        // format : 'Y-m-d H:i:s',
                        text : '车辆名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vCode',
                        text : '车牌号码'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'teamName',
                        text : '所属车队'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vLength',
                        text : '长度'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vWidth',
                        text : '宽度'
                    }],
                    dockedItems : [{
                        xtype : 'pagingtoolbar',
                        store : vStore,
                        dock : 'bottom',
                        displayInfo : true
                    }, {
                        xtype : 'toolbar',
                        dock : 'top',
                        items : [{
                            text : '添加车辆',
                            handler : me.addVehicle
                        }, '-', {
                            text : '删除',
                            handler : me.deleteVehicle
                        }]
                    }],
                    listeners : {
                        itemdblclick : {
                            fn : me.updateVehicle,
                            scope : me
                        }
                    }

                }]
            });
        }
        return win;
    },

    addVehicleTeam : function(btn) {
        var grid = btn.up('grid');
        var gridStore = grid.getStore();
        var createWin = Ext.create('ivp.view.vehicle.VehicleTeamForm', {
            gridStore : gridStore,
            title : '新建车队',
            udt : false,
            width : 500,
            height : 300
        });
        createWin.show();

    },

    onUpdateTeam : function(grid, record, item, index, e, eOpts) {
        var gridStore = grid.getStore();
        var updateWin = Ext.create('ivp.view.vehicle.VehicleTeamForm', {
            gridStore : gridStore,
            title : '修改车队',
            udt : true,
            logoPath : record.data.logoPath,
            width : 500,
            height : 380
        });
        updateWin.down('form').getForm().setValues(record.data);
        updateWin.show();

    },

    deleteVehicleTeam : function(btn) {
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
                url : 's/vt/dlt',
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

    onTeamSelect : function(rowmodel, record, index, eOpts) {

        var teampanel = rowmodel.view.up('gridpanel');

        var vehicleGrid = teampanel.up().down('gridpanel[region=center]');

        var vehicleStore = vehicleGrid.getStore();
        Ext.apply(vehicleStore.proxy.extraParams, {
            'vtID' : record.data.id
        });
        vehicleStore.load();

    },

    deleteVehicle : function(btn) {

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
                url : 's/v/dlt',
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

    addVehicle : function(btn) {
        var grid = btn.up('grid');

        var teamGrid = grid.up().down('gridpanel[region=west]');

        var record = teamGrid.getSelectionModel().getSelection()[0];

        if (!record) {
            Ext.MessageBox.alert('', '请选择车队');
            return false;
        }

        var gridStore = grid.getStore();
        var createWin = Ext.create('ivp.view.vehicle.VehicleForm', {
            gridStore : gridStore,
            title : '新建车辆',
            udt : false
        });
        createWin.show();

        createWin.down('form').getForm().setValues({
            'teamID' : record.data.id
        });

    },

    updateVehicle : function(grid, record, item, index, e, eOpts) {
        var gridStore = grid.getStore();
        var updateWin = Ext.create('ivp.view.vehicle.VehicleForm', {
            gridStore : gridStore,
            title : '修改车辆',
            udt : true
        });
        updateWin.down('form').getForm().setValues(record.data);
        updateWin.show();

    }

});