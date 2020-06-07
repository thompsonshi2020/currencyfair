app = {

    isMapReady : false,

    connect : function() {

        //var socket = new SockJS('/ws');
        var socket = new SockJS('/fall-websocket');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function() {
            // pull data initially
            app.loadData();

	        var error = '<div class="error">Did not find any data</div>';

            // set up websocket subscribers
            stompClient.subscribe('/topic/aggregateTrade', function (serverResponse) {
                    var data = JSON.parse(serverResponse.body);
                    data.forEach(function(d) {
                        d.tradeCount = +d.tradeCount;
                    });
    
                    if (data.length != 0) {
                        $('.error').remove();
                        app.drawPieChart(data);
                        app.drawTableTopCountryTrade(data);

                    } else {
                        $('#pie').html(error);
                        //app.isMapReady = false;
                    }
                });

            stompClient.subscribe('/topic/latestTrade', function (serverResponse) {
                var data = JSON.parse(serverResponse.body);
                data.forEach(function(d) {
                    d.tradeCount = +d.tradeCount;
                });
                if (data.length != 0) {
                    $('.error').remove();
                    
                    app.drawTable(data);
                } else {
	                $('#tradeTable').html(error);

                }
            });
        
        });
    },

    loadData : function() {
        $.ajax({
            url: '/loadtrade',
            type: 'GET'
        });
    },
    
    distinctArray : function(data) {
        //console.log('data: ' + data.length);
        var unique = [];
        var distinct = [];
        var pairlist = '';
        for( let i = 0; i < data.length; i++ ){
            //console.log('i: ' + i);
            //console.log('data[i]: ' + data[i]);
            if(!unique[data[i]]){
                //distinct.push(data[i]);
                pairlist = pairlist + ' ' +data[i]; 
                //console.log('distinct[data[i]]: ' + distinct[i]);
                unique[data[i]] = 1;
                //console.log('xxunique[data[i]]: ' + unique[data[i]]);
            }
        }
        return pairlist;
    },  

    drawTable : function(data) {
        var tab = "", x = "";

        tab += "<div class=\"container\">";
        tab += "<table border='1'>";

        tab += "<thead>";
        tab += "<tr>" + "<th> Trade ID </th>" + "<th> User ID </th>" + "<th> Originating Country </th>" + 
                        "<th> Currency From </th>" + "<th> Amount Sell </th>" +"<th> Currency To </th>" + 
                        "<th> Amount Buy </th>" + "<th> Success </th>" + "<th>Creation Time</th>";
                        
        tab += "</thead>";
        tab += "<tbody>";

        for (x in data) {
            tab += "<tr>";
            tab += "<td>" + data[x].tradeId + "</td>";
            tab += "<td>" + data[x].userId + "</td>";
            tab += "<td>" + data[x].originatingCountry + "</td>";
            tab += "<td>" + data[x].currencyFrom + "</td>";
            tab += "<td>" + data[x].amountSell + "</td>";
            tab += "<td>" + data[x].currencyTo + "</td>";            
            tab += "<td>" + data[x].amountBuy + "</td>";
            tab += "<td>" + data[x].tradeSuccess + "</td>";                        
            tab += "<td>" + data[x].timeCreated + "</td>";
            tab += "</tr>";            
        }

        tab += "</tbody>";
        tab += "</table>";
        tab += "</div>";

        document.getElementById("latestTrade").innerHTML = tab;

    },

    drawTableTopCountryTrade : function(data) {
        var tab = "", x = "";

        tab += "<div class=\"container\">";
        tab += "<table border='1'>";

        tab += "<thead>";
        tab += "<tr>" + "<th> Originating Country </th>" + "<th> Trade Count </th>" + "<th>Frequent Trade Pair</th>";
        tab += "</thead>";
        tab += "<tbody>";

        for (x in data) {
            tab += "<tr>";
            tab += "<td>" + data[x].originatingCountry + "</td>";
            tab += "<td>" + data[x].tradeCount + "</td>";
            tab += "<td>" + app.distinctArray(data[x].pairs) + "</td>";
            tab += "</tr>";            
        }

        tab += "</tbody>";
        tab += "</table>";
        tab += "</div>";

        document.getElementById("topCountryTradeCcy").innerHTML = tab;

    },    

    drawPieChart : function(data) {
        var width = 500,
            height = 500,
            r      = 200;

        var color = d3.scale.category20c();
        var arc = d3.svg.arc()
            .outerRadius(r - 10)
            .innerRadius(0);
        var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.tradeCount; });
        var pieData = pie(data);

        var svg = d3.select('#topCountryTrade').selectAll('svg').data([data]);
        svg.enter().append('svg').append('g');
        svg.attr('width', width).attr('height', height);

        // set up groups
        var g = svg.select('g')
            .attr('transform', 'translate(' + width/2 + ',' + height/2 + ')');

        // remove existing arcs in order to refresh it with new data
        g.selectAll('.arc').remove();

        var arcs = g.selectAll('.arc')
            .data(pieData);
        arcs.enter().append('g')
            .attr('class', 'arc')
            .attr('data-toggle', 'tooltip')
            .attr('title', function(d) {
                //console.log('Number of Trades: ' + d.data.tradeCount);
                return 'Number of Trades: ' + d.data.tradeCount;
            });

        // draw pieces
        arcs.append('path')
            .attr('d', arc)
            .style('fill', function(d) { return color(d.data.pairs); })

        // label pieces
        arcs.append('text')
            .attr('transform', function(d) { return 'translate(' + arc.centroid(d) + ')'; })
            .attr('dy', '.35em')
            .style('text-anchor', 'middle')
            //.text(function(d) { return d.data.pairs });
            .text(function(d) { return d.data.originatingCountry + ' - ' + d.data.tradeCount });
    },

    addLayer : function(svg, className, first) {

        var layer;
        if (first) {
            layer = svg.insert('g', ':first-child')
        } else {
            layer = svg.append('g')
        }
        return layer.attr('class', className || '');
    }
}