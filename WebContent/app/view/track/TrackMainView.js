/**
 * 车辆轨迹
 */
Ext.define('ivp.view.track.TrackMainView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.trackmainview',
    itemId : 'trackmainviewId',

    init : function() {
        this.launcher = {
            text : '车辆轨迹',
            iconCls : 'icon-grid'
        };
    },

    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('trackmainviewId');
        if (!win) {
            var trackStore = Ext.create('Ext.data.Store', {
                fields : ['id', 'vehicleID', 'testingVehicleID', ' vCode', 'vName', 'vLength', 'vWidth', 'gpsEName', 'gpsECode', 'testedTimes', 'testedTotalTimes',
                        'startTrackTime', 'stopTrackTime'],
                autoLoad : true,
                proxy : {
                    type : 'ajax',
                    url : 's/trk/s',
                    reader : {
                        type : "json",
                        root : "data",
                        successProperty : 'success',
                        totalProperty : 'total'
                    }
                }
            });

            win = desktop.createWindow({
                id : 'trackmainviewId',
                title : '车辆轨迹',
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
                    store : trackStore,
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
                        dataIndex : 'startTrackTime',
                        width : 160,
                        text : '开始时间'
                    }, {
                        xtype : 'gridcolumn',
                        width : 160,
                        dataIndex : 'stopTrackTime',
                        text : '结束时间'
                    }, {
                        xtype : 'gridcolumn',
                        width : 80,
                        dataIndex : 'testedTimes',
                        text : '测试序号'
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
                        dataIndex : 'testedTotalTimes',
                        text : '比赛(总)次数'

                    }],
                    dockedItems : [{
                        xtype : 'pagingtoolbar',
                        store : trackStore,
                        dock : 'bottom',
                        displayInfo : true
                    }, {
                        xtype : 'toolbar',
                        dock : 'top',
                        items : [{
                            text : '查看轨迹',
                            handler : me.viewTrack
                        }]
                    }]

                }]
            });
        }
        return win;
    },

    viewTrack : function(btn) {
        var grid = btn.up('grid');
        // var gridStore = grid.getStore();
        var record = grid.getSelectionModel().getSelection()[0];

        if (record) {
        } else {
            Ext.MessageBox.alert('', '您好像没有选择任何信息');
            return false
        }
        console.log();

        var createWin = Ext.create('ivp.view.track.TrackForm', {
            // gridStore : gridStore,
            title : '查看轨迹',
            tvID : record.data.testingVehicleID,
            tractTimes : record.data.testedTimes
        // udt : false
        });
        createWin.show();
    }

});
